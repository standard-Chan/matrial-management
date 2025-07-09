package com.springframework.mm.service;

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
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorCompanyService {

    private final VendorCompanyRepository companyRepository;
    private final VendorRepository vendorRepository;

    @Transactional
    public VendorCompany createVendorCompany(CompanyCreationRequest request) {
        Vendor vendor = vendorRepository.getVendorById(request.getVendorId())
                .orElseThrow(() -> new VendorException(ErrorCode.NOT_FOUND_VENDOR));
        return companyRepository.save(request.toEntity(vendor));
    }

    public List<VendorCompany> getAll() {
        return companyRepository.findAll();
    }

    @Transactional
    public List<VendorCompany> updateCompanies(List<CompanyUpdateRequest> requestList) {
        return requestList.stream().map(request -> {
            try {
                return updateCompany(request);
            } catch (OptimisticLockingFailureException e) {
                throw new VendorException(ErrorCode.CONFLICT_OPTIMISTIC_LOCK);
            }
        }).toList();
    }

    @Transactional
    public VendorCompany updateCompany(CompanyUpdateRequest request) {
        VendorCompany company = companyRepository.findById(request.getId())
                .orElseThrow(() -> new VendorCompanyException(ErrorCode.NOT_FOUND_COMPANY));

        company.setCompanyCode(request.getCompanyCode());
        company.setAccountCode(request.getAccountCode());
        company.setPaymentTermCode(request.getPaymentTermCode());

        return companyRepository.save(company);
    }

    @Transactional
    public void deleteCompanies(List<IdRequest> idList) {
        for (IdRequest id : idList) {
            companyRepository.deleteById(id.getId());
        }
    }

}
