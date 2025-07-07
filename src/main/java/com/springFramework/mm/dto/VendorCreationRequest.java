package com.springFramework.mm.dto;

import com.springFramework.mm.domain.Vendor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VendorCreationRequest {
    private String vendorCode;
    private String name;
    private String countryCode;
    private String vendorGroupCode;
    private String personalId;
    private String businessRegistrationNo;
    private String address;

    public Vendor toEntity() {
        return Vendor.builder()
                .vendorCode(vendorCode)
                .name(name)
                .countryCode(countryCode)
                .vendorGroupCode(vendorGroupCode)
                .personalId(personalId)
                .businessRegistrationNo(businessRegistrationNo)
                .address(address)
                .build();
    }
}
