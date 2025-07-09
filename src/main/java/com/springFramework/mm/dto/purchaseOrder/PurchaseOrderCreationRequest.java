package com.springframework.mm.dto.purchaseOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PurchaseOrderCreationRequest {
    private PurchaseOrderHeaderCreationRequest purchaseOrderHeader;
    private List<PurchaseOrderItemCreationRequest> items;
}
