package com.springFramework.mm.controller.api.vendor;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.dto.vendor.VendorUpdateRequest;
import com.springFramework.mm.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
