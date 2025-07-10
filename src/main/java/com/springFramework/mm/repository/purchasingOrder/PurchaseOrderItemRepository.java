package com.springframework.mm.repository.purchasingOrder;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM PurchaseOrderItem p WHERE p.id = :id")
    Optional<PurchaseOrderItem> findByIdWithOptimisticLock(@Param("id")Long id);
}
