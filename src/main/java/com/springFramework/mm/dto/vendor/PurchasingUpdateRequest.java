package com.springFramework.mm.dto.vendor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchasingUpdateRequest {
    private Long id;
    private String purchasingOrgCode;
    private String purchasingGroupCode;
    private String currency;
    private String taxCode;
}

