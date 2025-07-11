package com.springframework.mm.enums;

import lombok.Getter;

@Getter
public enum DocumentType {

    WE("Goods Receipt", "입고 전표"),
    WA("Goods Issue", "출고 전표"),
    MB("Stock Adjustment", "재고 조정"),
    UB("Stock Transfer", "창고 간 이동"),
    RE("Invoice Receipt", "세금계산서 수취"),
    GI("Goods Issue for Order", "오더 자재 소비"),
    PR("Reservation", "자재 예약");

    private final String description;
    private final String korean;

    DocumentType(String description, String korean) {
        this.description = description;
        this.korean = korean;
    }
}
