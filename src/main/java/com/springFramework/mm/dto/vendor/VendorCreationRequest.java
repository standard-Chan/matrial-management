package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.domain.Vendor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorCreationRequest {
    private String name;
    private String countryCode;
    private String vendorGroupCode;
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
