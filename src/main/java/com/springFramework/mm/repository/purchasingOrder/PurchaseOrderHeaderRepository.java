package com.springframework.mm.repository.purchasingOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderHeaderRepository extends JpaRepository<PurchaseOrderHeader, Long> {
}
