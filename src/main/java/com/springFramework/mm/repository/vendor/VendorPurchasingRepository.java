package com.springFramework.mm.repository.vendor;

import com.springFramework.mm.domain.vendor.VendorPurchasing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorPurchasingRepository extends JpaRepository<VendorPurchasing, Long> {
}
