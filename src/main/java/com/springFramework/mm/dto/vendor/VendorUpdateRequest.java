package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.enums.VendorGroupCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorUpdateRequest {
    private Long id;
    private String name;
    private String countryCode;
    private VendorGroupCode vendorGroupCode;
    private String personalId;
    private String businessRegistrationNo;
    private String address;
}