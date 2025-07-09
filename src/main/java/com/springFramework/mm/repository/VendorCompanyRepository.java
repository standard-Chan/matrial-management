package com.springFramework.mm.repository;

import com.springFramework.mm.domain.vendor.VendorCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorCompanyRepository extends JpaRepository<VendorCompany, Long> {
}
