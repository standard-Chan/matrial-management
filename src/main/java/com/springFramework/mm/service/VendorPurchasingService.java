package com.springFramework.mm.service;

import com.springFramework.mm.domain.vendor.Vendor;
import com.springFramework.mm.domain.vendor.VendorPurchasing;
import com.springFramework.mm.dto.common.IdRequest;
import com.springFramework.mm.dto.vendor.PurchasingCreationRequest;
import com.springFramework.mm.dto.vendor.PurchasingUpdateRequest;
import com.springFramework.mm.enums.ErrorCode;
import com.springFramework.mm.exception.vendor.VendorException;
import com.springFramework.mm.exception.vendor.VendorPurchasingException;
import com.springFramework.mm.repository.VendorPurchasingRepository;
import com.springFramework.mm.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorPurchasingService {

    private final VendorRepository vendorRepository;
    private final VendorPurchasingRepository vendorPurchasingRepository;

    public void createVendorPurchasing(PurchasingCreationRequest request) {
        Vendor vendor = vendorRepository.getVendorById(request.getVendorId())
                .orElseThrow(() -> new VendorException(ErrorCode.NOT_FOUND_VENDOR));
        vendorPurchasingRepository.save(request.toEntity(vendor));
    }

    public List<VendorPurchasing> getAllPurchasing() {
        return vendorPurchasingRepository.findAll();
    }

    public List<VendorPurchasing> updatePurchasings(List<PurchasingUpdateRequest> requestList) {
        return requestList.stream().map((request) -> {
            try {
                return updatePurchasing(request);
            } catch (OptimisticLockingFailureException e) {
                throw new VendorException(ErrorCode.CONFLICT_OPTIMISTIC_LOCK);
            }
        }).toList();
    }

    public VendorPurchasing updatePurchasing(PurchasingUpdateRequest request) {
        VendorPurchasing purchasing = vendorPurchasingRepository.findById(request.getId())
                .orElseThrow(() -> new VendorPurchasingException(ErrorCode.NOT_FOUND_PURCHASING));

        purchasing.setPurchasingOrgCode(request.getPurchasingOrgCode());
        purchasing.setPurchasingGroupCode(request.getPurchasingGroupCode());
        purchasing.setCurrency(request.getCurrency());
        purchasing.setTaxCode(request.getTaxCode());

        return vendorPurchasingRepository.save(purchasing);
    }

    public void deletePurchasings(List<IdRequest> idList) {
        for (IdRequest id : idList) {
            vendorPurchasingRepository.deleteById(id.getId());
        }
    }
}
