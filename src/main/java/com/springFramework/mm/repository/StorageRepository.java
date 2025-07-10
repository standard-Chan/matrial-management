package com.springframework.mm.repository;

import com.springframework.mm.domain.Storage;
import com.springframework.mm.domain.vendor.VendorCompany;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> getStorageById(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s FROM Storage s WHERE s.id = :id")
    Optional<Storage> findByIdWithOptimisticLock(@Param("id")Long id);
}
