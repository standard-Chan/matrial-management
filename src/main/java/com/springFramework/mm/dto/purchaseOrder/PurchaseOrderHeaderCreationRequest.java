package com.springframework.mm.dto.purchaseOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorCompany;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
/** 구매 오더 헤더 생성 요청 dto */
public class PurchaseOrderHeaderCreationRequest {
    private Long vendorCompanyId;
    private LocalDate orderDate;

    public PurchaseOrderHeader toEntity(VendorCompany vendorCompany) {
        return PurchaseOrderHeader.builder()
                .vendorCompany(vendorCompany)
                .orderDate(orderDate)
                .build();
    }
}
