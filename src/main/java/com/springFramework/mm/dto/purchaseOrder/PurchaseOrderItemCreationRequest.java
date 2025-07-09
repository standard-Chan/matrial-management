package com.springframework.mm.dto.purchaseOrder;

import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItemCreationRequest {
    private Long itemNo; // 구매 오더 품목 번호
    private Long materialId;
    private Long quantity;
    private LocalDate deliveryDate;
    private Long storageId;

    public PurchaseOrderItem toEntity(PurchaseOrderHeader purchaseOrderHeader, Material material, Storage storage) {
        return PurchaseOrderItem.builder()
                .itemNo(itemNo)
                .deliveryDate(deliveryDate)
                .quantity(quantity)
                .purchaseOrderHeader(purchaseOrderHeader)
                .material(material)
                .storage(storage)
                .build();

    }
}
