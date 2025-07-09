package com.springframework.mm.enums;

import lombok.Getter;

@Getter
public enum QuantityUnit {
    EA("EA", "개"),
    BOX("BOX", "박스"),
    ROLL("ROLL", "롤"),
    KG("KG", "킬로그램"),
    G("G", "그램"),
    MG("MG", "밀리그램"),
    L("L", "리터"),
    ML("ML", "밀리리터"),
    M("M", "미터"),
    CM("CM", "센티미터"),
    MM("MM", "밀리미터"),
    SET("SET", "세트"),
    PACK("PACK", "팩");

    private final String code;
    private final String description;

    QuantityUnit(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

