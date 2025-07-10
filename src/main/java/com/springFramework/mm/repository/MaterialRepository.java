package com.springframework.mm.repository;

import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.vendor.VendorCompany;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> getMaterialById(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT m FROM Material m WHERE m.id = :id")
    Optional<Material> findByIdWithOptimisticLock(@Param("id")Long id);
}
