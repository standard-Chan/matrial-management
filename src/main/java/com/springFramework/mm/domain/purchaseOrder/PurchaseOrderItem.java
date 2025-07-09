package com.springframework.mm.domain.purchaseOrder;

import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.vendorOrder.VendorOrderItemException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PurchaseOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    private Long quantity;

    private LocalDateTime deliveryDate;  // 납품 예정일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrderHeader_id")
    private PurchaseOrderHeader purchaseOrderHeader;

    /** 수량이 0 이하 인지 확인 */
    public void checkQuantity() {
        if (this.quantity < 1)
            throw new VendorOrderItemException(ErrorCode.INVALID_QUANTITY);
    }
}
