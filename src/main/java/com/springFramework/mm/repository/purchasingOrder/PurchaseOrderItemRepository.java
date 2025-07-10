package com.springframework.mm.repository.purchasingOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {

    @Query("""
        SELECT i FROM PurchaseOrderItem i
        JOIN FETCH i.purchaseOrderHeader
        JOIN FETCH i.material
        JOIN FETCH i.storage s
        JOIN FETCH s.facility
    """)
    List<PurchaseOrderItem> findAllWithJoins();
}
