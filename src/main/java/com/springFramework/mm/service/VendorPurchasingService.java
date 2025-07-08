package com.springFramework.mm.service;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorPurchasing;
import com.springFramework.mm.dto.common.IdRequest;
import com.springFramework.mm.dto.vendor.PurchasingCreationRequest;
import com.springFramework.mm.dto.vendor.PurchasingUpdateRequest;
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

    public void updatePurchasings(List<PurchasingUpdateRequest> requestList) {
        for (PurchasingUpdateRequest request : requestList) {
            VendorPurchasing purchasing = vendorPurchasingRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("구매조직 ID " + request.getId() + " 없음"));

            purchasing.setPurchasingOrgCode(request.getPurchasingOrgCode());
            purchasing.setPurchasingGroupCode(request.getPurchasingGroupCode());
            purchasing.setCurrency(request.getCurrency());
            purchasing.setTaxCode(request.getTaxCode());

            vendorPurchasingRepository.save(purchasing);
        }
    }

    public void deletePurchasings(List<IdRequest> idList) {
        for (IdRequest id : idList) {
            vendorPurchasingRepository.deleteById(id.getId());
        }
    }
}
