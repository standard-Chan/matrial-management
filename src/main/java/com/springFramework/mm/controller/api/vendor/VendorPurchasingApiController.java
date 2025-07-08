package com.springFramework.mm.controller.api.vendor;

import com.springFramework.mm.dto.common.IdRequest;
import com.springFramework.mm.dto.vendor.PurchasingUpdateRequest;
import com.springFramework.mm.service.VendorPurchasingService;
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
        vendorPurchasingService.updatePurchasings(requestList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deletePurchasings(@RequestBody List<IdRequest> idList) {
        vendorPurchasingService.deletePurchasings(idList);
        return ResponseEntity.noContent().build();
    }
}

