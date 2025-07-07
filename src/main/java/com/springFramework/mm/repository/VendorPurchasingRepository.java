package com.springFramework.mm.repository;

import com.springFramework.mm.domain.VendorPurchasing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorPurchasingRepository extends JpaRepository<VendorPurchasing, Long> {
}
