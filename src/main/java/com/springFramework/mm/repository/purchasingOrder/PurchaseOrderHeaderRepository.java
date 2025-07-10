package com.springframework.mm.repository.purchasingOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderHeaderRepository extends JpaRepository<PurchaseOrderHeader, Long> {

    @Query("""
        SELECT p FROM PurchaseOrderHeader p
        JOIN FETCH p.vendorCompany vc
        JOIN FETCH vc.vendor
    """)
    List<PurchaseOrderHeader> findAllWithCompanyAndVendor();
}
