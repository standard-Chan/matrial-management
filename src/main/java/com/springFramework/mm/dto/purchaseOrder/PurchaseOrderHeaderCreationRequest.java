package com.springframework.mm.dto.purchaseOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.vendor.Vendor;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
/** 구매 오더 헤더 생성 요청 dto */
public class PurchaseOrderHeaderCreationRequest {
    private Long vendorId;
    private LocalDate orderDate;

    public PurchaseOrderHeader toEntity(Vendor vendor) {
        return PurchaseOrderHeader.builder()
                .vendor(vendor)
                .orderDate(orderDate)
                .build();
    }
}
