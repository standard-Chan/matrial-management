package util;

import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.domain.Facility;
import com.springframework.mm.dto.vendor.CompanyCreationRequest;
import com.springframework.mm.dto.vendor.VendorCreationRequest;
import com.springframework.mm.dto.vendor.VendorUpdateRequest;
import com.springframework.mm.enums.AccountCode;
import com.springframework.mm.enums.PaymentTermCode;
import com.springframework.mm.enums.QuantityUnit;
import com.springframework.mm.enums.VendorGroupCode;

/** 테스트 시 Entity 생성 용 클래스 */
public class Creation {

    public static VendorCreationRequest createVendorRequest(String name, String country) {
        return VendorCreationRequest.builder()
                .name(name)
                .countryCode(country)
                .vendorGroupCode(VendorGroupCode.DOMESTIC)
                .personalId("850101-1234567")
                .businessRegistrationNo("1650212345")
                .address("서울시 강남구")
                .build();
    }

    public static VendorUpdateRequest createUpdateRequest(Long id, String name, String countryCode){
        return VendorUpdateRequest.builder()
                .id(id)
                .name(name)
                .countryCode(countryCode)
                .vendorGroupCode(VendorGroupCode.DOMESTIC)
                .personalId("updated:850101-1234567")
                .businessRegistrationNo("updated:1650212345")
                .address("서울시 강남구2")
                .build();
    }

    public static CompanyCreationRequest createCompany(Long vendorId) {
        return CompanyCreationRequest.builder()
                .vendorId(vendorId)
                .companyCode("1000")
                .accountCode(AccountCode.PAYABLE_DOMESTIC)
                .paymentTermCode(PaymentTermCode.M001)
                .build();
    }

    public static Material createMaterial(String name, QuantityUnit unit, int price) {
        return Material.builder()
                .name(name)
                .baseUnit(unit)
                .price(price)
                .build();
    }

    public static Storage createStorage(String name, Facility facility) {
        return Storage.builder()
                .name(name)
                .facility(facility)
                .build();
    }
}
