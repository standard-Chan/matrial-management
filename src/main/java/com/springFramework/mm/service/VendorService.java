package com.springFramework.mm.service;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.dto.common.IdRequest;
import com.springFramework.mm.dto.vendor.VendorCreationRequest;
import com.springFramework.mm.dto.vendor.VendorUpdateRequest;
import com.springFramework.mm.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    @Transactional
    public void createVendor(VendorCreationRequest request) {
        vendorRepository.save(request.toEntity());
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Transactional
    public List<Vendor> updateVendors(List<VendorUpdateRequest> requests) {
        return requests.stream().map(request -> {
            Vendor vendor = vendorRepository.getVendorById(request.getId())
                    .orElseThrow(()-> new EntityNotFoundException());

            vendor.setName(request.getName());
            vendor.setPersonalId(request.getPersonalId());
            vendor.setBusinessRegistrationNo(request.getBusinessRegistrationNo());
            vendor.setVendorGroupCode(request.getVendorGroupCode());
            vendor.setCountryCode(request.getCountryCode());
            vendor.setAddress(request.getAddress());

            return vendorRepository.save(vendor);
        }).toList();
    }

    @Transactional
    public void deleteVendors(List<IdRequest> vendorIdList) {
        vendorIdList.forEach(request -> vendorRepository.deleteById(request.getId()));
    }
}
