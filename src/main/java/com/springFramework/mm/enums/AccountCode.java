package com.springFramework.mm.enums;

import lombok.Getter;

@Getter
public enum AccountCode {
    PAYABLE_DOMESTIC("2100001010", "외상매입금_국내"),
    PAYABLE_IMPORT("2100001020", "외상매입금_수입"),
    PAYABLE_OTHER("2100001030", "외상매입금_기타"),
    NOTE_PAYABLE("2100001040", "지급어음"),
    ACCRUED_DOMESTIC("2100002010", "미지급금_국내"),
    ACCRUED_FOREIGN("2100002020", "미지급금_외화"),
    ACCRUED_CORPORATE_CARD("2100002030", "미지급금_법인카드"),
    ACCRUED_INTEREST("2100003010", "미지급비용_지급이자"),
    ACCRUED_SALARY("2100003020", "미지급비용_급여"),
    ACCRUED_RETIRE("2100003030", "미지급비용_퇴직금"),
    ACCRUED_BONUS("2100003040", "미지급비용_상여급"),
    ACCRUED_ETC("2100003050", "미지급비용_기타"),
    ACCRUED_DIVIDEND("2100004010", "미지급배당금"),
    ACCRUED_CORPORATE_TAX("2100005010", "미지급법인세");

    private final String code;
    private final String description;

    AccountCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
