package com.springframework.mm.controller.api.vendor;

import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.vendor.VendorUpdateRequest;
import com.springframework.mm.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendors")
@RequiredArgsConstructor
public class VendorApiController {

    private final VendorService vendorService;

    @PutMapping()
    public ResponseEntity<List<Vendor>> updateVendors(@RequestBody List<VendorUpdateRequest> vendorList) {
        return ResponseEntity.ok(vendorService.updateVendors(vendorList));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVendors(@RequestBody List<IdRequest> vendorIdList) {
        vendorService.deleteVendors(vendorIdList);
        return ResponseEntity.status(204).build();
    }
}
