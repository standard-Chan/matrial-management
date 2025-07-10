package com.springframework.mm.repository.purchasingOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderHeaderRepository extends JpaRepository<PurchaseOrderHeader, Long> {

    @Query("""
        SELECT p FROM PurchaseOrderHeader p
        JOIN FETCH p.vendorCompany vc
        JOIN FETCH vc.vendor
    """)
    List<PurchaseOrderHeader> findAllWithCompanyAndVendor();

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM PurchaseOrderHeader p WHERE p.id = :id")
    Optional<PurchaseOrderHeader> findByIdWithOptimisticLock(@Param("id")Long id);
}
