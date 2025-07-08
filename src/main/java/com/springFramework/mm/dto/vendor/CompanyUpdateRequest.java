package com.springFramework.mm.dto.vendor;

import com.springFramework.mm.enums.AccountCode;
import com.springFramework.mm.enums.PaymentTermCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequest {
    private Long id;
    private String companyCode;
    private AccountCode accountCode;
    private PaymentTermCode paymentTermCode;
}
