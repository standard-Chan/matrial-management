package com.springframework.mm.controller.api.purchaseOrder;

import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemUpdateRequest;
import com.springframework.mm.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderApiController {

    private final PurchaseOrderService purchaseOrderService;

    @PutMapping
    public ResponseEntity<Void> updateItems(@RequestBody List<PurchaseOrderItemUpdateRequest> requests) {
        purchaseOrderService.updatePurchaseOrderItems(requests);
        return ResponseEntity.ok().build();
    }
}

