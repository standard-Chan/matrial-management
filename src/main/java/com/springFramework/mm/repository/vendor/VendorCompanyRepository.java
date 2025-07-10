package com.springframework.mm.repository.vendor;

import com.springframework.mm.domain.vendor.VendorCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorCompanyRepository extends JpaRepository<VendorCompany, Long> {
    Optional<VendorCompany> getVendorCompanyById(Long id);

    @Query("SELECT c FROM VendorCompany c JOIN FETCH c.vendor")
    List<VendorCompany> findAllWithVendor();
}
