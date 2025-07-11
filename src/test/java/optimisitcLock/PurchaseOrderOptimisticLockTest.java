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
import com.springframework.mm.enums.QuantityUnit;
import com.springframework.mm.exception.purchaseOrder.PurchaseOrderItemException;
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
import org.springframework.transaction.annotation.Transactional;
import util.TestEntityFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MaterialManagerApplication.class)
public class PurchaseOrderOptimisticLockTest {

    @Autowired private VendorRepository vendorRepository;
    @Autowired private VendorCompanyRepository vendorCompanyRepository;
    @Autowired private MaterialRepository materialRepository;
    @Autowired private StorageRepository storageRepository;
    @Autowired private PurchaseOrderHeaderRepository headerRepository;
    @Autowired private PurchaseOrderItemRepository itemRepository;
    @Autowired private EntityManager em;
    @Autowired private FacilityRepository facilityRepository;
    @Autowired private PurchaseOrderService purchaseOrderService;

    public Long itemId;
    public Long matId;
    public Long storageId;
    public Long vendorId;
    public int successCount = 0;

    @BeforeEach
    void createItem() {
        // Vendor, Company, Facility, Storage, Material 생성 및 저장
        Vendor vendor = vendorRepository.saveAndFlush(TestEntityFactory.createVendor("삼성전자"));
        vendorId = vendor.getId();
        VendorCompany company = vendorCompanyRepository.saveAndFlush(TestEntityFactory.createVendorCompany(vendor, "1000"));
        Facility facility = facilityRepository.saveAndFlush(TestEntityFactory.createFacility("서울공장"));


        Storage storage = storageRepository.saveAndFlush(TestEntityFactory.createStorage("메인창고", facility));
        Material mat = TestEntityFactory.createMaterial("테스트자재", QuantityUnit.EA, 1000L);
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

        // 첫 시도만 성공
        assertThat(successCount).isEqualTo(1);

    }

}
