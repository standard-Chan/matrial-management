package com.springFramework.mm.service;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorCompany;
import com.springFramework.mm.domain.VendorPurchasing;
import com.springFramework.mm.dto.vendor.VendorCreationRequest;
import com.springFramework.mm.dto.vendor.CompanyCreationRequest;
import com.springFramework.mm.dto.vendor.PurchasingCreationRequest;
import com.springFramework.mm.repository.VendorCompanyRepository;
import com.springFramework.mm.repository.VendorPurchasingRepository;
import com.springFramework.mm.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final VendorCompanyRepository companyRepository;
    private final VendorPurchasingRepository purchasingRepository;


    public void createVendor(VendorCreationRequest request) {
        vendorRepository.save(request.toEntity());
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

}
