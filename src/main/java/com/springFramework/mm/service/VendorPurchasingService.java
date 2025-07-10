package com.springframework.mm.service;

import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorPurchasing;
import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.vendor.PurchasingCreationRequest;
import com.springframework.mm.dto.vendor.PurchasingUpdateRequest;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.vendor.VendorException;
import com.springframework.mm.exception.vendor.VendorPurchasingException;
import com.springframework.mm.repository.vendor.VendorPurchasingRepository;
import com.springframework.mm.repository.vendor.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class VendorPurchasingService {

    private final VendorRepository vendorRepository;
    private final VendorPurchasingRepository vendorPurchasingRepository;

    public void createVendorPurchasing(PurchasingCreationRequest request) {
        log.info("구매 정보 등록 요청 수신 - vendorId: {}", request.getVendorId());

        Vendor vendor = vendorRepository.getVendorById(request.getVendorId())
                .orElseThrow(() -> {
                    log.warn("구매 정보 등록 실패 - 존재하지 않는 vendorId: {}", request.getVendorId());
                    return new VendorException(ErrorCode.NOT_FOUND_VENDOR);
                });

        vendorPurchasingRepository.save(request.toEntity(vendor));
        log.info("구매 정보 등록 완료 - vendorId: {}", request.getVendorId());
    }

    public List<VendorPurchasing> getAllPurchasing() {
        log.info("전체 구매 정보 목록 조회 요청 수신");
        List<VendorPurchasing> result = vendorPurchasingRepository.findAll();
        log.debug("조회된 구매 정보 수: {}", result.size());
        return result;
    }

    public List<VendorPurchasing> updatePurchasings(List<PurchasingUpdateRequest> requestList) {
        log.info("구매 정보 일괄 수정 요청 수신 - 요청 수: {}", requestList.size());

        return requestList.stream().map((request) -> {
            try {
                return updatePurchasing(request);
            } catch (OptimisticLockingFailureException e) {
                log.warn("낙관적 락 충돌 발생 - purchasingId: {}", request.getId());
                throw new VendorException(ErrorCode.CONFLICT_OPTIMISTIC_LOCK);
            }
        }).toList();
    }

    public VendorPurchasing updatePurchasing(PurchasingUpdateRequest request) {
        log.debug("구매 정보 수정 시도 - purchasingId: {}", request.getId());

        VendorPurchasing purchasing = vendorPurchasingRepository.findById(request.getId())
                .orElseThrow(() -> {
                    log.warn("구매 정보 수정 실패 - 존재하지 않는 purchasingId: {}", request.getId());
                    return new VendorPurchasingException(ErrorCode.NOT_FOUND_PURCHASING);
                });

        purchasing.setPurchasingOrgCode(request.getPurchasingOrgCode());
        purchasing.setPurchasingGroupCode(request.getPurchasingGroupCode());
        purchasing.setCurrency(request.getCurrency());
        purchasing.setTaxCode(request.getTaxCode());

        VendorPurchasing updated = vendorPurchasingRepository.save(purchasing);
        log.debug("구매 정보 수정 완료 - purchasingId: {}", updated.getId());
        return updated;
    }

    public void deletePurchasings(List<IdRequest> idList) {
        log.info("구매 정보 일괄 삭제 요청 수신 - 삭제 대상 수: {}", idList.size());

        for (IdRequest id : idList) {
            log.debug("구매 정보 삭제 시도 - purchasingId: {}", id.getId());
            vendorPurchasingRepository.deleteById(id.getId());
        }

        log.info("구매 정보 일괄 삭제 완료 - 삭제 건수: {}", idList.size());
    }
}
