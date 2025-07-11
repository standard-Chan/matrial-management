package com.springframework.mm.domain.goodsReceipt;

import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "goods_receipt_item",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_goods_receipt_header_purchase_order_item_fiscal_year",
                        columnNames = {}

                )
        }
)
public class GoodsReceiptItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 전표 번호 PK

    @ManyToOne
    @JoinColumn(name = "goods_receipt_header_id")
    private GoodsReceiptHeader goodsReceiptHeader;  // 입고 헤더

    @ManyToOne
    @JoinColumn(name = "purchase_order_item_id")
    private PurchaseOrderItem purchaseOrderItem;  // 구매 오더 품목

    @Column(length = 4, nullable = false)
    private String fiscalYear;

    private int amount;  // 금액
}
