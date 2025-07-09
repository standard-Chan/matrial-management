package com.springframework.mm.repository.purchasingOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
}
