package optimisitcLock;

import com.springframework.mm.MaterialManagerApplication;
import com.springframework.mm.domain.Facility;
import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorCompany;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemUpdateRequest;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.enums.QuantityUnit;
import com.springframework.mm.exception.MaterialException;
import com.springframework.mm.repository.FacilityRepository;
import com.springframework.mm.repository.MaterialRepository;
import com.springframework.mm.repository.StorageRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderHeaderRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderItemRepository;
import com.springframework.mm.repository.vendor.VendorCompanyRepository;
import com.springframework.mm.repository.vendor.VendorRepository;
import com.springframework.mm.service.PurchaseOrderService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import util.TestEntityFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MaterialManagerApplication.class)
public class PurchaseOrderOptimisticLockTest {

    @Autowired private VendorRepository vendorRepository;
    @Autowired private VendorCompanyRepository vendorCompanyRepository;
    @Autowired private MaterialRepository materialRepository;
    @Autowired private StorageRepository storageRepository;
    @Autowired private PurchaseOrderHeaderRepository headerRepository;
    @Autowired private PurchaseOrderItemRepository itemRepository;
    @Autowired private FacilityRepository facilityRepository;
    @Autowired private PurchaseOrderService purchaseOrderService;
    @Autowired private PlatformTransactionManager transactionManager; // 트랜잭션 열기 용
    @Autowired private EntityManager em;

    public Long itemId;
    public Long matId;
    public Long storageId;
    public Long vendorId;
    public int successCount = 0;
    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @BeforeEach
    void createItem() {
        // Vendor, Company, Facility, Storage, Material 생성 및 저장
        Vendor vendor = vendorRepository.saveAndFlush(TestEntityFactory.createVendor("삼성전자"));
        vendorId = vendor.getId();
        VendorCompany company = vendorCompanyRepository.saveAndFlush(TestEntityFactory.createVendorCompany(vendor, "1000"));
        Facility facility = facilityRepository.saveAndFlush(TestEntityFactory.createFacility("서울공장"));


        Storage storage = storageRepository.saveAndFlush(TestEntityFactory.createStorage("메인창고", facility));
        Material mat = TestEntityFactory.createMaterial("테스트자재", QuantityUnit.EA, 1000);
        Material material = materialRepository.saveAndFlush(mat);
        PurchaseOrderHeader header = headerRepository.saveAndFlush(TestEntityFactory.createPurchaseOrderHeader(company));

        matId = material.getId();
        storageId = storage.getId();

        this.itemId = itemRepository.saveAndFlush(TestEntityFactory.createPurchaseOrderItem(
                material, storage, header, 10L, LocalDate.now().plusDays(5))).getId();

    }

    @Test
    /** 첫 요청만 반영되고, 나머지 요청은 충돌로 미반영 */
    void 병렬_UPDATE_낙관적락_충돌_테스트() throws InterruptedException {
        // given
        PurchaseOrderItem item = itemRepository.findByIdWithAll(itemId);
        Long materialId = item.getMaterial().getId();
        Long storageId = item.getStorage().getId();
        LocalDate deliveryDate = item.getDeliveryDate();


        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);



        // when
        for (int i = 0; i < 5; i++) {
            int threadNo = i + 1;
            executor.submit(() -> {
                try {
                    Thread.sleep(200); // 일부러 겹치게 타이밍 조절
                    PurchaseOrderItemUpdateRequest request = PurchaseOrderItemUpdateRequest.builder()
                            .id(itemId)
                            .quantity(100L + threadNo) // 값만 다르게
                            .deliveryDate(deliveryDate)
                            .materialId(materialId)
                            .storageId(storageId)
                            .build();

                    purchaseOrderService.updatePurchaseOrderItems(List.of(request));
                    System.out.println("[Thread-" + threadNo + "] 업데이트 성공 O");
                    successCount ++;

                } catch (ObjectOptimisticLockingFailureException e) {
                    System.out.println("[Thread-" + threadNo + "] 낙관적 락 충돌 발생 X. " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 작업 완료 대기
        executor.shutdown();

        // then
        // 첫 시도만 성공
        assertThat(successCount).isEqualTo(1);
    }

    @Test
    void 연관_Material_UPDATE_중_낙관적락_충돌_테스트() throws Exception {
        PurchaseOrderItem item = itemRepository.findByIdWithAll(itemId);
        Long materialId = item.getMaterial().getId();

        CountDownLatch materialFetchedLatch = new CountDownLatch(1); // A가 material 조회 완료하면 신호
        CountDownLatch materialUpdatedLatch = new CountDownLatch(1); // B가 material 업데이트 완료하면 A 시작
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Thread A
        executor.submit(() -> {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            // 외부 쓰레드 환경에서 직접 트랜잭션 열기
            TransactionStatus status = transactionManager.getTransaction(def);

            try {
                // 1. Material 조회
                Material material = materialRepository.findByIdWithOptimisticLock(materialId)
                        .orElseThrow(() -> new MaterialException(ErrorCode.NOT_FOUND_MATERIAL));
                System.out.println("[Thread-A] Material 조회 완료, version = " + material.getVersion());
                materialFetchedLatch.countDown();

                materialUpdatedLatch.await(); // B가 끝날 때까지 대기
                assertThat(material.getVersion()).isEqualTo(0);
                System.out.println("[Thread-A] 쓰레드 B 종료 후 Material 조회 완료, version = " + material.getVersion());

                // 3. PurchaseOrderItem 업데이트 시도
                PurchaseOrderItem purchaseOrderItem = purchaseOrderItemRepository.findByIdWithOptimisticLock(itemId)
                        .orElseThrow();

                purchaseOrderItem.setItemNo(3L);
                purchaseOrderItem.setMaterial(material);

                Long beforeMatVersion = material.getVersion();


                Material newMat = materialRepository.findById(materialId).orElseThrow();
                em.refresh(newMat);

                if (!Objects.equals(beforeMatVersion, newMat.getVersion())) {
                    throw new ObjectOptimisticLockingFailureException(Material.class, material.getId());}

                purchaseOrderItemRepository.saveAndFlush(purchaseOrderItem); // 여기서 version 비교 발생

                transactionManager.commit(status); // flush & commit

                System.out.println("[Thread-A] 업데이트 성공 X (예상과 다름)");

            } catch (ObjectOptimisticLockingFailureException e) {
                transactionManager.rollback(status);
                System.out.println("[Thread-A] 낙관적 락 충돌 O : " + e.getMessage());
            } catch (Exception e) {
                transactionManager.rollback(status);
                e.printStackTrace();
            }
        });

        // Thread B
        executor.submit(() -> {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            TransactionStatus status = transactionManager.getTransaction(def);

            try {
                materialFetchedLatch.await();
                Thread.sleep(300); // A와 겹치게

                Material material = materialRepository.findById(materialId)
                        .orElseThrow(() -> new MaterialException(ErrorCode.NOT_FOUND_MATERIAL));
                material.setName("충돌용자재");
                material.setPrice(12345);

                assertThat(material.getVersion()).isEqualTo(0);
                System.out.println("[Thread-B] Material 업데이트 전, version = " + material.getVersion());


                materialRepository.saveAndFlush(material); // version 증가

                transactionManager.commit(status);

                Material material2 = materialRepository.findById(materialId)
                        .orElseThrow(() -> new MaterialException(ErrorCode.NOT_FOUND_MATERIAL));
                assertThat(material2.getVersion()).isEqualTo(1);
                System.out.println("[Thread-B] Material 업데이트 + flush 완료, version = " + material2.getVersion());

            } catch (Exception e) {
                transactionManager.rollback(status);
                e.printStackTrace();
            } finally {
                materialUpdatedLatch.countDown();
            }
        });

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
