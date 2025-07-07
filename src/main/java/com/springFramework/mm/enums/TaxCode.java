package com.springFramework.mm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum TaxCode {
    V1("V1", "매입일반10%"),
    V2("V2", "매입계산0%"),
    V3("V3", "매입영세0%"),
    V4("V4", "매입불공제"),
    V5("V5", "매입고정자산");

    private final String code;
    private final String description;

    TaxCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
