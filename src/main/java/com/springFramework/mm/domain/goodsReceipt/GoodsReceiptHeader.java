package com.springframework.mm.domain.goodsReceipt;

import com.springframework.mm.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GoodsReceiptHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4, nullable = false)
    private String fiscalYear;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType; // 전표 유형

    @Column(nullable = false)
    private LocalDate documentDate; // 증빙일

    private LocalDate postingDate; // 전기일


}
