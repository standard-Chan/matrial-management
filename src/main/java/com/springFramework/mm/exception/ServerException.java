package com.springframework.mm.exception;

import com.springframework.mm.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerException extends RuntimeException {
    private final ErrorCode errorCode;

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
