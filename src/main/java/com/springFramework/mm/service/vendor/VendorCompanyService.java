package com.springframework.mm.service.vendor;

import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorCompany;
import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.vendor.CompanyCreationRequest;
import com.springframework.mm.dto.vendor.CompanyUpdateRequest;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.vendor.VendorCompanyException;
import com.springframework.mm.exception.vendor.VendorException;
import com.springframework.mm.repository.vendor.VendorCompanyRepository;
import com.springframework.mm.repository.vendor.VendorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class VendorCompanyService {

    private final VendorCompanyRepository companyRepository;
    private final VendorRepository vendorRepository;

    @Transactional
    public VendorCompany createVendorCompany(CompanyCreationRequest request) {
        log.info("회사 등록 요청 수신 - vendorId: {}", request.getVendorId());

        Vendor vendor = vendorRepository.getVendorById(request.getVendorId())
                .orElseThrow(() -> {
                    log.warn("회사 등록 실패 - 존재하지 않는 vendorId: {}", request.getVendorId());
                    return new VendorException(ErrorCode.NOT_FOUND_VENDOR);
                });

        VendorCompany savedCompany = companyRepository.save(request.toEntity(vendor));
        log.info("회사 등록 완료 - companyId: {}", savedCompany.getId());
        return savedCompany;
    }

    public List<VendorCompany> getAll() {
        log.info("전체 회사 목록 조회 요청 수신");
        List<VendorCompany> result = companyRepository.findAll();
        log.debug("조회된 회사 수: {}", result.size());
        return result;
    }

    @Transactional
    public List<VendorCompany> updateCompanies(List<CompanyUpdateRequest> requestList) {
        log.info("회사 일괄 수정 요청 수신 - 요청 수: {}", requestList.size());

        return requestList.stream().map(request -> {
            try {
                return updateCompany(request);
            } catch (OptimisticLockingFailureException e) {
                log.warn("낙관적 락 충돌 발생 - companyId: {}", request.getId());
                throw new VendorException(ErrorCode.CONFLICT_OPTIMISTIC_LOCK);
            }
        }).toList();
    }

    @Transactional
    public VendorCompany updateCompany(CompanyUpdateRequest request) {
        log.debug("회사 수정 시도 - companyId: {}", request.getId());

        VendorCompany company = companyRepository.findById(request.getId())
                .orElseThrow(() -> {
                    log.warn("회사 수정 실패 - 존재하지 않는 companyId: {}", request.getId());
                    return new VendorCompanyException(ErrorCode.NOT_FOUND_COMPANY);
                });

        company.setCompanyCode(request.getCompanyCode());
        company.setAccountCode(request.getAccountCode());
        company.setPaymentTermCode(request.getPaymentTermCode());

        VendorCompany updated = companyRepository.save(company);
        log.debug("회사 수정 완료 - companyId: {}", updated.getId());
        return updated;
    }

    @Transactional
    public void deleteCompanies(List<IdRequest> idList) {
        log.info("회사 일괄 삭제 요청 수신 - 삭제 대상 수: {}", idList.size());

        for (IdRequest id : idList) {
            log.debug("회사 삭제 시도 - companyId: {}", id.getId());
            companyRepository.deleteById(id.getId());
        }

        log.info("회사 일괄 삭제 완료 - 삭제 건수: {}", idList.size());
    }
}
