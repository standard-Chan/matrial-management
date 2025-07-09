package vendor;

import com.springframework.mm.MaterialManagerApplication;
import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorCompany;
import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.vendor.CompanyCreationRequest;
import com.springframework.mm.dto.vendor.CompanyUpdateRequest;
import com.springframework.mm.repository.vendor.VendorCompanyRepository;
import com.springframework.mm.repository.vendor.VendorRepository;
import com.springframework.mm.service.VendorCompanyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.springframework.mm.enums.AccountCode.PAYABLE_DOMESTIC;
import static com.springframework.mm.enums.AccountCode.PAYABLE_IMPORT;
import static com.springframework.mm.enums.PaymentTermCode.M001;
import static com.springframework.mm.enums.PaymentTermCode.M002;
import static com.springframework.mm.enums.VendorGroupCode.DOMESTIC;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = MaterialManagerApplication.class)
@ActiveProfiles("test")
class VendorCompanyServiceTest {

    @Autowired
    private VendorCompanyService vendorCompanyService;

    @Autowired
    private VendorCompanyRepository vendorCompanyRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @AfterEach
    void cleanup() {
        vendorCompanyRepository.deleteAll();
        vendorRepository.deleteAll();
    }

    private Vendor prepareVendor() {
        return vendorRepository.save(Vendor.builder()
                .name("벤더")
                .countryCode("KR")
                .vendorGroupCode(DOMESTIC)
                .personalId("000000-0000000")
                .businessRegistrationNo("1234567890")
                .address("서울")
                .build());
    }

    @Test
    void 회사코드_정상등록() {
        // given
        Vendor vendor = prepareVendor();
        CompanyCreationRequest request = new CompanyCreationRequest(vendor.getId(), "1000", PAYABLE_DOMESTIC, M001);

        // when
        VendorCompany result = vendorCompanyService.createVendorCompany(request);

        // then
        List<VendorCompany> all = vendorCompanyRepository.findAll();
        assertThat(all).hasSize(1);
        assertThat(result.getCompanyCode()).isEqualTo("1000");
    }

    @Test
    void 회사코드_수정() {
        // given
        Vendor vendor = prepareVendor();
        VendorCompany company = vendorCompanyRepository.save(
                VendorCompany.builder()
                        .vendor(vendor)
                        .companyCode("1000")
                        .accountCode(PAYABLE_DOMESTIC)
                        .paymentTermCode(M001)
                        .build()
        );

        CompanyUpdateRequest updateRequest = new CompanyUpdateRequest(
                company.getId(),
                "2000",
                PAYABLE_IMPORT,
                M002
        );

        // when
        vendorCompanyService.updateCompanies(List.of(updateRequest));

        // then
        VendorCompany updated = vendorCompanyRepository.findById(company.getId()).orElseThrow();
        assertThat(updated.getCompanyCode()).isEqualTo("2000");
        assertThat(updated.getAccountCode().getCode()).isEqualTo("2100001020");
        assertThat(updated.getPaymentTermCode().getCode()).isEqualTo("M002");
    }

    @Test
    void 회사코드_전체조회() {
        // given
        Vendor vendor = prepareVendor();
        vendorCompanyRepository.save(VendorCompany.builder()
                .vendor(vendor)
                .companyCode("1000")
                .accountCode(PAYABLE_DOMESTIC)
                .paymentTermCode(M001)
                .build());

        vendorCompanyRepository.save(VendorCompany.builder()
                .vendor(vendor)
                .companyCode("2000")
                .accountCode(PAYABLE_DOMESTIC)
                .paymentTermCode(M002)
                .build());

        // when
        List<VendorCompany> result = vendorCompanyService.getAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    void 회사코드_삭제() {
        // given
        Vendor vendor = prepareVendor();
        VendorCompany company = vendorCompanyRepository.save(
                VendorCompany.builder()
                        .vendor(vendor)
                        .companyCode("1000")
                        .accountCode(PAYABLE_DOMESTIC)
                        .paymentTermCode(M001)
                        .build()
        );

        IdRequest idRequest = new IdRequest();
        idRequest.setId(company.getId());

        // when
        vendorCompanyService.deleteCompanies(List.of(idRequest));

        // then
        boolean exists = vendorCompanyRepository.existsById(company.getId());
        assertThat(exists).isFalse();
    }
}