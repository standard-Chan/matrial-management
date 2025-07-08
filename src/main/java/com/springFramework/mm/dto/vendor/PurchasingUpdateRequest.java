package com.springFramework.mm.dto.vendor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchasingUpdateRequest {
    private Long id;
    private String purchasingOrgCode;
    private String purchasingGroupCode;
    private String currency;
    private String taxCode;
}

