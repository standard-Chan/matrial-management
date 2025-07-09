package com.springframework.mm.dto.vendor;

import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorPurchasing;
import com.springframework.mm.enums.TaxCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PurchasingCreationRequest {
    private Long vendorId;
    private String purchasingOrgCode;
    private String purchasingGroupCode;
    private String currency;
    private TaxCode taxCode;

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
