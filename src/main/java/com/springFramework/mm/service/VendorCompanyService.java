package com.springFramework.mm.service;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorCompany;
import com.springFramework.mm.dto.vendor.CompanyCreationRequest;
import com.springFramework.mm.repository.VendorCompanyRepository;
import com.springFramework.mm.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorCompanyService {

    private final VendorCompanyRepository companyRepository;
    private final VendorRepository vendorRepository;

    public void createVendorCompany(CompanyCreationRequest request) {
        Vendor vendor = vendorRepository.getVendorById(request.getVendorId())
                .orElseThrow(() -> new EntityNotFoundException());
        companyRepository.save(request.toEntity(vendor));
    }

    public List<VendorCompany> getAll() {
        return companyRepository.findAll();
    }
}
