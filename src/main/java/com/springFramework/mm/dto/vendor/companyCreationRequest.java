package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorCompany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class companyCreationRequest {
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
