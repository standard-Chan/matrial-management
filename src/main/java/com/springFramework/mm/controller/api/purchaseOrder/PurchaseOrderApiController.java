package com.springframework.mm.controller.api.purchaseOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemUpdateRequest;
import com.springframework.mm.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderApiController {

    private final PurchaseOrderService purchaseOrderService;

    @PutMapping
    public ResponseEntity<List<PurchaseOrderItem>> updateItems(@RequestBody List<PurchaseOrderItemUpdateRequest> requests) {
        List<PurchaseOrderItem> response = purchaseOrderService.updatePurchaseOrderItems(requests);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteItems(@RequestBody List<IdRequest> requests) {
        purchaseOrderService.deletePurchaseOrderItems(requests);
        return ResponseEntity.ok().build();
    }
}

