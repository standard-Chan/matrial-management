package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorCompany;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyCreationRequest {
    private Long vendorId;
    private String companyCode;
    private String accountCode;
    private String paymentTermCode;

    public VendorCompany toEntity(Vendor vendor) {
        return VendorCompany.builder()
                .vendor(vendor)
                .accountCode(accountCode)
                .companyCode(companyCode)
                .paymentTermCode(paymentTermCode)
                .build();
    }
}
