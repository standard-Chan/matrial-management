package com.springFramework.mm.dto.vendor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequest {
    private Long id;
    private String companyCode;
    private String accountCode;
    private String paymentTermCode;
}
