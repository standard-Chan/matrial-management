package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.enums.TaxCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasingUpdateRequest {
    private Long id;
    private String purchasingOrgCode;
    private String purchasingGroupCode;
    private String currency;
    private TaxCode taxCode;
}

