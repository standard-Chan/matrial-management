package com.springFramework.mm.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 400
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    MISSING_REQUIRED_VALUE(HttpStatus.BAD_REQUEST, "필수 값이 누락되었습니다."),

    // 404
    NOT_FOUND_VENDOR(HttpStatus.NOT_FOUND, "해당 vendor를 찾을 수 없습니다."),
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, "해당 vendor company를 찾을 수 없습니다."),
    NOT_FOUND_PURCHASING(HttpStatus.NOT_FOUND, "해당 vendor purchasing을 찾을 수 없습니다."),

    // 409
    CONFLICT_RELATION_EXISTS(HttpStatus.CONFLICT, "연관관계가 존재하여 삭제할 수 없습니다."),
    CONFLICT_OPTIMISTIC_LOCK(HttpStatus.CONFLICT, "다른 사용자가 값을 수정하였습니다. 다시 시도해주세요. (CONFLICT OPTIMISTIC LOCK)"),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
