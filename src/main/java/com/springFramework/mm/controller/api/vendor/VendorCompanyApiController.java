package com.springFramework.mm.controller.api.vendor;

import com.springFramework.mm.dto.common.IdRequest;
import com.springFramework.mm.dto.vendor.CompanyUpdateRequest;
import com.springFramework.mm.service.VendorCompanyService;
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
