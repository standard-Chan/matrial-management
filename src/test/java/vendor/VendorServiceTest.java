package vendor;

import com.springFramework.mm.MaterialManagerApplication;
import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorCompany;
import com.springFramework.mm.dto.common.IdRequest;
import com.springFramework.mm.dto.vendor.CompanyCreationRequest;
import com.springFramework.mm.dto.vendor.VendorCreationRequest;
import com.springFramework.mm.dto.vendor.VendorUpdateRequest;
import com.springFramework.mm.exception.vendor.VendorException;
import com.springFramework.mm.repository.VendorCompanyRepository;
import com.springFramework.mm.repository.VendorRepository;
import com.springFramework.mm.service.VendorCompanyService;
import com.springFramework.mm.service.VendorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static com.springFramework.mm.enums.AccountCode.PAYABLE_DOMESTIC;
import static com.springFramework.mm.enums.PaymentTermCode.M001;
import static com.springFramework.mm.enums.VendorGroupCode.DOMESTIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = MaterialManagerApplication.class)
@ActiveProfiles("test")
public class VendorServiceTest {

    @Autowired
    private VendorService vendorService;
    @Autowired
    private VendorCompanyService vendorCompanyService;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private VendorCompanyRepository vendorCompanyRepository;

    @AfterEach
    void 종료() {
        vendorRepository.deleteAll();
    }

    @Test
    void 구매처_등록() {
        // given
        VendorCreationRequest request = VendorCreationRequest.builder()
                .name("신세계푸드")
                .countryCode("KR")
                .vendorGroupCode(DOMESTIC)
                .personalId("850101-1234567")
                .businessRegistrationNo("1650212345")
                .address("서울시 서초구 양재동")
                .build();

        // when
        vendorService.createVendor(request);

        // then
        List<Vendor> all = vendorRepository.findAll();
        assertThat(all).hasSize(1);

        Vendor vendor = all.get(0);
        assertThat(vendor.getName()).isEqualTo("신세계푸드");
    }

    @Test
    void 구매처_수정() {
        // given
        Vendor v1 = vendorService.createVendor(createVendorRequest("신세계푸드", "KR"));
        Vendor v2 = vendorService.createVendor(createVendorRequest("삼성", "USA"));

        List<VendorUpdateRequest> dtos = new ArrayList<>();
        dtos.add(createUpdateRequest(v1.getId(), "구세계푸드", "EU"));
        dtos.add(createUpdateRequest(v2.getId(), "오성", "KR"));

        // when
        vendorService.updateVendors(dtos);

        // then
        List<Vendor> all = vendorRepository.findAll();

        Vendor vendor1 = all.get(0);
        Vendor vendor2 = all.get(1);
        assertThat(vendor1.getName()).isEqualTo("구세계푸드");
        assertThat(vendor2.getCountryCode()).isEqualTo("KR");
    }

    @Test
    void 구매처_삭제() {
        // given
        Vendor v = vendorService.createVendor(createVendorRequest("숭실대", "KR"));
        List<IdRequest> list = new ArrayList<>();
        IdRequest ir = new IdRequest();
        ir.setId(v.getId());
        list.add(ir);

        // when
        vendorService.deleteVendors(list);

        // then
        List<Vendor> all = vendorService.getAllVendors();
        assertThat(all).hasSize(0);
    }

    @Test
    void 연관관계_구매처_삭제() {
        //given
        Vendor vendor_foreign = vendorService.createVendor(createVendorRequest("연관관계 테스트", "KR"));
        List<IdRequest> list = new ArrayList<>();
        IdRequest ir = new IdRequest();
        ir.setId(vendor_foreign.getId());
        list.add(ir);

        // when
        // 연관 관계 생성
        VendorCompany vc = vendorCompanyService.createVendorCompany(createCompany(vendor_foreign.getId()));

        // then
        // 연관관계 삭제 방지 에러
        assertThrows(VendorException.class, () -> {
            vendorService.deleteVendors(list);
            vendorRepository.flush();
        });

        // 정상적인 종료를 위한 company 삭제 코드 (after Each 코드)
        vendorCompanyRepository.deleteAll();
        vendorCompanyRepository.flush();
    }

    private VendorCreationRequest createVendorRequest(String name, String country) {
        return VendorCreationRequest.builder()
                .name(name)
                .countryCode(country)
                .vendorGroupCode(DOMESTIC)
                .personalId("850101-1234567")
                .businessRegistrationNo("1650212345")
                .address("서울시 강남구")
                .build();
    }

    private VendorUpdateRequest createUpdateRequest(Long id, String name, String countryCode){
        return VendorUpdateRequest.builder()
                .id(id)
                .name(name)
                .countryCode(countryCode)
                .vendorGroupCode(DOMESTIC)
                .personalId("updated:850101-1234567")
                .businessRegistrationNo("updated:1650212345")
                .address("서울시 강남구2")
                .build();
    }

    private CompanyCreationRequest createCompany(Long vendorId) {
        return CompanyCreationRequest.builder()
                .vendorId(vendorId)
                .companyCode("1000") // 회사 코드
                .accountCode(PAYABLE_DOMESTIC) // 외상매입금_국내
                .paymentTermCode(M001) // 지급 코드
                .build();

    }
}
