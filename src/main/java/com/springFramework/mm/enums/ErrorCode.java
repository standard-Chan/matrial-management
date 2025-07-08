package com.springFramework.mm.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 400
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    MISSING_REQUIRED_VALUE(HttpStatus.BAD_REQUEST, "필수 값이 누락되었습니다."),

    // 404
    NOT_FOUND_VENDOR(HttpStatus.NOT_FOUND, "해당 벤더를 찾을 수 없습니다."),
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, "해당 회사코드를 찾을 수 없습니다."),
    NOT_FOUND_PURCHASING(HttpStatus.NOT_FOUND, "해당 구매조직을 찾을 수 없습니다."),

    // 409
    CONFLICT_RELATION_EXISTS(HttpStatus.CONFLICT, "연관관계가 존재하여 삭제할 수 없습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
