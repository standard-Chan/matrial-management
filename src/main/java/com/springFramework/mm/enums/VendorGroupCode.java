package com.springframework.mm.enums;

import lombok.Getter;

@Getter
public enum VendorGroupCode {
    DOMESTIC("1000", "국내"),
    OVERSEAS("2000", "해외"),
    INDIVIDUAL("3000", "개인"),
    DEPARTMENT("4000", "부서"),
    CORPORATE_CARD("5000", "법인카드"),
    FINANCIAL_INSTITUTION("6000", "금융기관"),
    ONE_TIME("9000", "1회성");

    private final String code;
    private final String description;

    VendorGroupCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String toValue() {
        return this.code;
    }

    public static VendorGroupCode fromCode(String code) {
        for (VendorGroupCode v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
