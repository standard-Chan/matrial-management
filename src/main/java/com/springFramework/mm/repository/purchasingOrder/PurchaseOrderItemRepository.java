package com.springFramework.mm.repository.purchasingOrder;

import com.springFramework.mm.domain.purchaseOrder.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
}
