package com.springframework.mm.dto.vendor;

import com.springframework.mm.enums.TaxCode;
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

