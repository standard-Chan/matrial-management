package com.springFramework.mm.repository.purchasingOrder;

import com.springFramework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderHeaderRepository extends JpaRepository<PurchaseOrderHeader, Long> {
}
