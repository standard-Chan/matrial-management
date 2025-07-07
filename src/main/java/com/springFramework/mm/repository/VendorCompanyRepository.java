package com.springFramework.mm.repository;

import com.springFramework.mm.domain.VendorCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorCompanyRepository extends JpaRepository<VendorCompany, Long> {
}
