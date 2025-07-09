package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.domain.vendor.Vendor;
import com.springFramework.mm.domain.vendor.VendorCompany;
import com.springFramework.mm.enums.AccountCode;
import com.springFramework.mm.enums.PaymentTermCode;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyCreationRequest {
    private Long vendorId;
    private String companyCode;
    private AccountCode accountCode;
    private PaymentTermCode paymentTermCode;

    public VendorCompany toEntity(Vendor vendor) {
        return VendorCompany.builder()
                .vendor(vendor)
                .accountCode(accountCode)
                .companyCode(companyCode)
                .paymentTermCode(paymentTermCode)
                .build();
    }
}
