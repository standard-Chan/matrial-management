package com.springframework.mm.controller.api.vendor;

import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.vendor.CompanyUpdateRequest;
import com.springframework.mm.service.vendor.VendorCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor-companies")
@RequiredArgsConstructor
public class VendorCompanyApiController {

    private final VendorCompanyService vendorCompanyService;

    @PutMapping
    public ResponseEntity<?> updateCompanies(@RequestBody List<CompanyUpdateRequest> requestList) {
        return ResponseEntity.ok(vendorCompanyService.updateCompanies(requestList));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompanies(@RequestBody List<IdRequest> idList) {
        vendorCompanyService.deleteCompanies(idList);
        return ResponseEntity.noContent().build();
    }
}
