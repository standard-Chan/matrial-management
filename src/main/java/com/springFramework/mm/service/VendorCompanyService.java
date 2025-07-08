package com.springFramework.mm.service;

import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.domain.VendorCompany;
import com.springFramework.mm.dto.common.IdRequest;
import com.springFramework.mm.dto.vendor.CompanyCreationRequest;
import com.springFramework.mm.dto.vendor.CompanyUpdateRequest;
import com.springFramework.mm.repository.VendorCompanyRepository;
import com.springFramework.mm.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new EntityNotFoundException());
        return companyRepository.save(request.toEntity(vendor));
    }

    public List<VendorCompany> getAll() {
        return companyRepository.findAll();
    }

    @Transactional
    public void updateCompanies(List<CompanyUpdateRequest> requestList) {
        for (CompanyUpdateRequest request : requestList) {
            // id 기준으로 수정
            VendorCompany company = companyRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Not Found"));

            company.setCompanyCode(request.getCompanyCode());
            company.setAccountCode(request.getAccountCode());
            company.setPaymentTermCode(request.getPaymentTermCode());

            companyRepository.save(company);
        }
    }

    @Transactional
    public void deleteCompanies(List<IdRequest> idList) {
        for (IdRequest id : idList) {
            companyRepository.deleteById(id.getId());
        }
    }

}
