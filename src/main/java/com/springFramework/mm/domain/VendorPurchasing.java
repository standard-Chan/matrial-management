package com.springFramework.mm.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class VendorPurchasing {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_code", referencedColumnName = "vendorCode")
    private Vendor vendor;

    private String companyCode;    // 회사 코드
    private String accountCode;    // 계좌 코드
    private String paymentTermCode; // 지급 조건
}
