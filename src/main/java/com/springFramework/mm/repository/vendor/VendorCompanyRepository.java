package com.springframework.mm.repository.vendor;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import com.springframework.mm.domain.vendor.VendorCompany;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorCompanyRepository extends JpaRepository<VendorCompany, Long> {
    Optional<VendorCompany> getVendorCompanyById(Long id);

    @Query("SELECT c FROM VendorCompany c JOIN FETCH c.vendor")
    List<VendorCompany> findAllWithVendor();

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT v FROM VendorCompany v WHERE v.id = :id")
    Optional<VendorCompany> findByIdWithOptimisticLock(@Param("id")Long id);
}
