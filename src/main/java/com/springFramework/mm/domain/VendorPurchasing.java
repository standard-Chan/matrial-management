package com.springFramework.mm.domain;

import com.springFramework.mm.enums.TaxCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(     // 제약조건 생성
        name = "vendor_purchasing",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_vendor_purchasing_org_code_purchasing_group_code",
                        columnNames = {"vendor_id", "purchasing_org_code","purchasing_group_code"}
                )
        }
)
public class VendorPurchasing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    private String purchasingOrgCode;  // 구매조직 코드
    private String purchasingGroupCode;  // 구매그룹 코드
    private String currency;  // 구매오더 통화

    @Enumerated(EnumType.STRING)
    private TaxCode taxCode;  // 세금 코드


}
