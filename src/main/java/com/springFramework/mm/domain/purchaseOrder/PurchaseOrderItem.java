package com.springframework.mm.domain.purchaseOrder;

import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.purchaseOrder.PurchaseOrderItemException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(     // 제약조건 생성
        name = "purchase_order_item",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_purchase_order_header_item_no",
                        columnNames = {"purchase_order_header", "item_no"}
                )
        }
)
public class PurchaseOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrderHeader_id")
    private PurchaseOrderHeader purchaseOrderHeader;

    private Long itemNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    private Long quantity;

    private LocalDate deliveryDate;  // 납품 예정일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Version
    private Long version;

    /** 수량이 0 이하 인지 확인 */
    public void checkQuantity() {
        if (this.quantity < 1)
            throw new PurchaseOrderItemException(ErrorCode.INVALID_QUANTITY);
    }
}
