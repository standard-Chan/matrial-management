package com.springframework.mm.enums;

import lombok.Getter;

@Getter
public enum PaymentTermCode {
    M001("M001", "당월10일지급"),
    M002("M002", "당월25일지급"),
    M003("M003", "익월10일지급"),
    M004("M004", "익월25일지급"),
    M005("M005", "익익월10일지급"),
    M006("M006", "익익월25일지급"),
    P010("P010", "10일이내지급"),
    P015("P015", "15일이내지급"),
    P020("P020", "20일이내지급"),
    P030("P030", "30일이내지급"),
    P060("P060", "60일이내지급"),
    P090("P090", "90일이내지급");

    private final String code;
    private final String description;

    PaymentTermCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

