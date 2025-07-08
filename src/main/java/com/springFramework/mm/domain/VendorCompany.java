package com.springFramework.mm.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(     // 제약조건 생성
        name = "vendor_company",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_vendor_companycode",
                        columnNames = {"vendor_id", "company_code"}
                )
        }
)
public class VendorCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor; // 구매처 코드

    @Column(unique = true)
    private String companyCode; // 회사 코드

    private String accountCode; // 계정 코드

    private String paymentTermCode; // 지급 조건
}
