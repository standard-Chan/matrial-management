package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorPurchasing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PurchasingCreationRequest {
    private Long vendorId;
    private String purchasingOrgCode;
    private String purchasingGroupCode;
    private String currency;
    private String taxCode;

    public VendorPurchasing toEntity(Vendor vendor) {
        return VendorPurchasing.builder()
                .vendor(vendor)
                .purchasingOrgCode(this.purchasingOrgCode)
                .purchasingGroupCode(this.purchasingGroupCode)
                .currency(this.currency)
                .taxCode(this.taxCode)
                .build();
    }
}
