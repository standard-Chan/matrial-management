package com.springframework.mm.controller.api.vendor;

import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.vendor.PurchasingUpdateRequest;
import com.springframework.mm.service.VendorPurchasingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor-purchasings")
@RequiredArgsConstructor
public class VendorPurchasingApiController {

    private final VendorPurchasingService vendorPurchasingService;

    @PutMapping
    public ResponseEntity<?> updatePurchasings(@RequestBody List<PurchasingUpdateRequest> requestList) {
        return ResponseEntity.ok(vendorPurchasingService.updatePurchasings(requestList));
    }

    @DeleteMapping
    public ResponseEntity<?> deletePurchasings(@RequestBody List<IdRequest> idList) {
        vendorPurchasingService.deletePurchasings(idList);
        return ResponseEntity.noContent().build();
    }
}

