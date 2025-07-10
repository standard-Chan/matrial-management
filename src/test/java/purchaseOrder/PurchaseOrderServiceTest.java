package purchaseOrder;

import com.springframework.mm.MaterialManagerApplication;
import com.springframework.mm.domain.Facility;
import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorCompany;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderHeaderCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemCreationRequest;
import com.springframework.mm.enums.QuantityUnit;
import com.springframework.mm.repository.*;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderHeaderRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderItemRepository;
import com.springframework.mm.repository.vendor.VendorCompanyRepository;
import com.springframework.mm.repository.vendor.VendorRepository;
import com.springframework.mm.service.PurchaseOrderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Creation.*;

@SpringBootTest(classes = MaterialManagerApplication.class)
@ActiveProfiles("test")
@Transactional
class PurchaseOrderServiceTest {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private VendorCompanyRepository vendorCompanyRepository;
    @Autowired
    private PurchaseOrderHeaderRepository headerRepository;
    @Autowired
    private PurchaseOrderItemRepository itemRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private FacilityRepository facilityRepository;

    @Test
    @DisplayName("구매오더 저장 테스트")
    void createPurchaseOrderTest() {
        // Vendor & Company 생성
        Vendor vendor = vendorRepository.save(createVendorRequest("테스트거래처", "KR").toEntity());
        VendorCompany company = vendorCompanyRepository.save(createCompany(vendor.getId()).toEntity(vendor));

        // Material 생성
        Material material = materialRepository.save(createMaterial("테스트자재", QuantityUnit.EA, 2000L));

        // Storage + Facility 생성
        Facility facility = facilityRepository.save(Facility.builder().name("시설A").address("서울") .build());
        Storage storage = storageRepository.save(createStorage("창고1", facility));

        // 구매오더 헤더 request 생성
        PurchaseOrderHeaderCreationRequest headerRequest = new PurchaseOrderHeaderCreationRequest();
        headerRequest.setVendorCompanyId(company.getId());
        headerRequest.setOrderDate(LocalDate.now());

        // 구매오더 아이템 request 생성
        PurchaseOrderItemCreationRequest itemRequest = PurchaseOrderItemCreationRequest.builder()
                .itemNo(1L)
                .materialId(material.getId())
                .quantity(3L)
                .deliveryDate(LocalDate.now().plusDays(5))
                .storageId(storage.getId())
                .build();

        PurchaseOrderCreationRequest request = new PurchaseOrderCreationRequest();
        request.setPurchaseOrderHeader(headerRequest);
        request.setItems(List.of(itemRequest));

        // when
        purchaseOrderService.createPurchaseOrder(request);

        // then
        List<PurchaseOrderHeader> headers = headerRepository.findAll();
        assertThat(headers).hasSize(1);
        assertThat(headers.get(0).getVendorCompany().getVendor().getName()).isEqualTo("테스트거래처");

        assertThat(itemRepository.findAll()).hasSize(1);
        assertThat(itemRepository.findAll().get(0).getMaterial().getName()).isEqualTo("테스트자재");
    }
}