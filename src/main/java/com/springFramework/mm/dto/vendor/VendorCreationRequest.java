package com.springframework.mm.dto.vendor;

import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.enums.VendorGroupCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorCreationRequest {
    private String name;
    private String countryCode;
    private VendorGroupCode vendorGroupCode;
    private String personalId;
    private String businessRegistrationNo;
    private String address;

    public Vendor toEntity() {
        return Vendor.builder()
                .name(name)
                .countryCode(countryCode)
                .vendorGroupCode(vendorGroupCode)
                .personalId(personalId)
                .businessRegistrationNo(businessRegistrationNo)
                .address(address)
                .build();
    }
}
