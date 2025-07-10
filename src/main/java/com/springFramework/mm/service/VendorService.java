package com.springframework.mm.service;

import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.vendor.VendorCreationRequest;
import com.springframework.mm.dto.vendor.VendorUpdateRequest;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.vendor.VendorException;
import com.springframework.mm.repository.vendor.VendorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    @Transactional
    public Vendor createVendor(VendorCreationRequest request) {
        log.info("구매처 등록 요청 수신 - 이름: {}", request.getName());

        Vendor vendor = request.toEntity();
        Vendor saved = vendorRepository.save(vendor);

        log.info("구매처 등록 완료 - id: {}", saved.getId());
        return saved;
    }

    public List<Vendor> getAllVendors() {
        log.info("전체 구매처 목록 조회 요청 수신");
        List<Vendor> result = vendorRepository.findAll();
        log.debug("조회된 구매처 수: {}", result.size());
        return result;
    }

    @Transactional
    public List<Vendor> updateVendors(List<VendorUpdateRequest> requests) {
        log.info("구매처 일괄 수정 요청 수신 - 요청 수: {}", requests.size());

        return requests.stream().map(request -> {
            try {
                return this.updateVendor(request);
            } catch (OptimisticLockingFailureException e) {
                log.warn("구매처 수정 실패 - 낙관적 락 충돌 발생: vendorId: {}", request.getId());
                throw new VendorException(ErrorCode.CONFLICT_OPTIMISTIC_LOCK);
            }
        }).toList();
    }

    @Transactional
    public Vendor updateVendor(VendorUpdateRequest request) {
        log.debug("구매처 수정 시도 - vendorId: {}", request.getId());

        Vendor vendor = vendorRepository.getVendorById(request.getId())
                .orElseThrow(() -> {
                    log.warn("구매처 수정 실패 - 존재하지 않는 vendorId: {}", request.getId());
                    return new VendorException(ErrorCode.NOT_FOUND_VENDOR);
                });

        vendor.setName(request.getName());
        vendor.setPersonalId(request.getPersonalId());
        vendor.setBusinessRegistrationNo(request.getBusinessRegistrationNo());
        vendor.setVendorGroupCode(request.getVendorGroupCode());
        vendor.setCountryCode(request.getCountryCode());
        vendor.setAddress(request.getAddress());

        Vendor updated = vendorRepository.save(vendor);
        log.debug("구매처 수정 완료 - vendorId: {}", updated.getId());
        return updated;
    }

    @Transactional
    public void deleteVendors(List<IdRequest> vendorIdList) {
        log.info("구매처 일괄 삭제 요청 수신 - 삭제 대상 수: {}", vendorIdList.size());

        try {
            for (IdRequest request : vendorIdList) {
                log.debug("구매처 삭제 시도 - vendorId: {}", request.getId());
                vendorRepository.deleteById(request.getId());
            }
            vendorRepository.flush();
            log.info("구매처 일괄 삭제 완료 - 삭제 건수: {}", vendorIdList.size());
        } catch (DataIntegrityViolationException e) {
            log.warn("구매처 삭제 실패 - 관계 제약 존재");
            throw new VendorException(ErrorCode.CONFLICT_RELATION_EXISTS);
        }
    }
}
