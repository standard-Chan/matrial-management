package com.springFramework.mm.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Builder
public class VendorCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_code", referencedColumnName = "vendorCode")
    private Vendor vendor; // 구매처 코드

    private String companyCode; // 회사 코드

    private String accountCode; // 계정 코드

    private String paymentTermCode; // 지급 조건
}
