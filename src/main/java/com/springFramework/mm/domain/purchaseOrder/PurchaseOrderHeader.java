package com.springFramework.mm.domain.purchaseOrder;

import com.springFramework.mm.domain.vendor.Vendor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PurchaseOrderHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate; // 주문 일자

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 구매 오더 생성 시각

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
}
