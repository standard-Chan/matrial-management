package vendor;

import com.springframework.mm.MaterialManagerApplication;
import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorPurchasing;
import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.vendor.PurchasingCreationRequest;
import com.springframework.mm.dto.vendor.PurchasingUpdateRequest;
import com.springframework.mm.repository.vendor.VendorPurchasingRepository;
import com.springframework.mm.repository.vendor.VendorRepository;
import com.springframework.mm.service.VendorPurchasingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.springframework.mm.enums.TaxCode.V1;
import static com.springframework.mm.enums.TaxCode.V2;
import static com.springframework.mm.enums.VendorGroupCode.DOMESTIC;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = MaterialManagerApplication.class)
@ActiveProfiles("test")
public class VendorPurchasingServiceTest {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorPurchasingRepository purchasingRepository;

    @Autowired
    private VendorPurchasingService purchasingService;

    @AfterEach
    void cleanup() {
        purchasingRepository.deleteAll();
        vendorRepository.deleteAll();
    }

    private Vendor saveVendor() {
        return vendorRepository.save(Vendor.builder()
                .name("벤더테스트")
                .countryCode("KR")
                .vendorGroupCode(DOMESTIC)
                .personalId("850101-1234567")
                .businessRegistrationNo("1234567890")
                .address("서울시 강남구")
                .build());
    }

    @Test
    void 구매조직_정상등록() {
        // given
        Vendor vendor = saveVendor();
        PurchasingCreationRequest request = new PurchasingCreationRequest(
                vendor.getId(), "1000", "101", "KRW", V1
        );

        // when
        purchasingService.createVendorPurchasing(request);

        // then
        List<VendorPurchasing> all = purchasingRepository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getPurchasingOrgCode()).isEqualTo("1000");
    }

    @Test
    void 구매조직_조회() {
        // given
        Vendor vendor = saveVendor();
        purchasingRepository.save(VendorPurchasing.builder()
                .vendor(vendor)
                .purchasingOrgCode("1000")
                .purchasingGroupCode("101")
                .currency("KRW")
                .taxCode(V1)
                .build());

        // when
        List<VendorPurchasing> result = purchasingService.getAllPurchasing();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCurrency()).isEqualTo("KRW");
    }

    @Test
    void 구매조직_수정() {
        // given
        Vendor vendor = saveVendor();
        VendorPurchasing original = purchasingRepository.save(
                VendorPurchasing.builder()
                        .vendor(vendor)
                        .purchasingOrgCode("1000")
                        .purchasingGroupCode("101")
                        .currency("KRW")
                        .taxCode(V1)
                        .build()
        );

        PurchasingUpdateRequest update = new PurchasingUpdateRequest(
                original.getId(),
                "2000",
                "201",
                "USD",
                V2
        );

        // when
        purchasingService.updatePurchasings(List.of(update));

        // then
        VendorPurchasing updated = purchasingRepository.findById(original.getId()).orElseThrow();
        assertThat(updated.getPurchasingOrgCode()).isEqualTo("2000");
        assertThat(updated.getCurrency()).isEqualTo("USD");
    }

    @Test
    void 구매조직_삭제() {
        // given
        Vendor vendor = saveVendor();
        VendorPurchasing purchasing = purchasingRepository.save(
                VendorPurchasing.builder()
                        .vendor(vendor)
                        .purchasingOrgCode("1000")
                        .purchasingGroupCode("101")
                        .currency("KRW")
                        .taxCode(V1)
                        .build()
        );

        IdRequest request = new IdRequest();
        request.setId(purchasing.getId());

        // when
        purchasingService.deletePurchasings(List.of(request));

        // then
        boolean exists = purchasingRepository.existsById(purchasing.getId());
        assertThat(exists).isFalse();
    }
}
