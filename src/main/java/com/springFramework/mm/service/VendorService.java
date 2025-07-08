package com.springFramework.mm.service;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.dto.common.IdRequest;
import com.springFramework.mm.dto.vendor.VendorCreationRequest;
import com.springFramework.mm.dto.vendor.VendorUpdateRequest;
import com.springFramework.mm.enums.ErrorCode;
import com.springFramework.mm.exception.vendor.VendorException;
import com.springFramework.mm.repository.VendorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    @Transactional
    public Vendor createVendor(VendorCreationRequest request) {
        Vendor vendor = request.toEntity();
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Transactional
    /** vendor list Update + 낙관적 락 충돌 처리 */
    public List<Vendor> updateVendors(List<VendorUpdateRequest> requests) {
        return requests.stream().map(request -> {
            try {
                return this.updateVendor(request);
            } catch (OptimisticLockingFailureException e) {
                throw new VendorException(ErrorCode.CONFLICT_OPTIMISTIC_LOCK);
            }
        }).toList();
    }


    @Transactional
    /** 단일 Vendor Update */
    public Vendor updateVendor(VendorUpdateRequest request) {
        Vendor vendor = vendorRepository.getVendorById(request.getId())
                .orElseThrow(()-> new VendorException(ErrorCode.NOT_FOUND_VENDOR));

        vendor.setName(request.getName());
        vendor.setPersonalId(request.getPersonalId());
        vendor.setBusinessRegistrationNo(request.getBusinessRegistrationNo());
        vendor.setVendorGroupCode(request.getVendorGroupCode());
        vendor.setCountryCode(request.getCountryCode());
        vendor.setAddress(request.getAddress());

        return vendorRepository.save(vendor);
    }

    @Transactional
    public void deleteVendors(List<IdRequest> vendorIdList) {
        try {
            vendorIdList.forEach(request -> vendorRepository.deleteById(request.getId()));
            vendorRepository.flush(); // 예외 발생 여부를 확인하기 위해서 강제 commit
        } catch (DataIntegrityViolationException e) {
            throw new VendorException(ErrorCode.CONFLICT_RELATION_EXISTS);
        }
    }
}
