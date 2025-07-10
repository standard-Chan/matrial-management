package com.springframework.mm.dto.purchaseOrder;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItemUpdateRequest {
    private Long id;
    private Long quantity;
    private LocalDate deliveryDate;
    private Long materialId;
    private Long storageId;
}

