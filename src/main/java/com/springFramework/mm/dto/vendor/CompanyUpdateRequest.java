package com.springFramework.mm.dto.vendor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyUpdateRequest {
    private Long id;
    private String companyCode;
    private String accountCode;
    private String paymentTermCode;
}
