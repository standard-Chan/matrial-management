package com.springframework.mm.dto.vendor;

import com.springframework.mm.enums.AccountCode;
import com.springframework.mm.enums.PaymentTermCode;
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
