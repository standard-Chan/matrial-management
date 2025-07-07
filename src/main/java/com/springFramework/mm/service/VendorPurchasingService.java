package com.springFramework.mm.service;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorPurchasing;
import com.springFramework.mm.dto.vendor.PurchasingCreationRequest;
import com.springFramework.mm.repository.VendorPurchasingRepository;
import com.springFramework.mm.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorPurchasingService {

    private final VendorRepository vendorRepository;
    private final VendorPurchasingRepository vendorPurchasingRepository;

    public void createVendorPurchasing(PurchasingCreationRequest request) {
        Vendor vendor = vendorRepository.getVendorById(request.getVendorId())
                .orElseThrow(() -> new EntityNotFoundException());
        vendorPurchasingRepository.save(request.toEntity(vendor));
    }

    public List<VendorPurchasing> getAllPurchasing() {
        return vendorPurchasingRepository.findAll();
    }
}
