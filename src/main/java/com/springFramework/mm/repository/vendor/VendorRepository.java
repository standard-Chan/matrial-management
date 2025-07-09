package com.springFramework.mm.repository.vendor;

import com.springFramework.mm.domain.vendor.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> getVendorById(Long id);

    Vendor findVendorById(Long id);
}
