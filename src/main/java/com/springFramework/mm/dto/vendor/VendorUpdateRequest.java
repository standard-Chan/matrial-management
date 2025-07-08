package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.domain.Vendor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VendorUpdateRequest {
    private Long id;
    private String name;
    private String countryCode;
    private String vendorGroupCode;
    private String personalId;
    private String businessRegistrationNo;
    private String address;
}