package com.springframework.mm.dto.vendor;

import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorCompany;
import com.springframework.mm.enums.AccountCode;
import com.springframework.mm.enums.PaymentTermCode;
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
