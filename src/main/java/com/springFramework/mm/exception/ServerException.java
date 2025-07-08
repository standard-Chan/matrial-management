package com.springFramework.mm.exception;

import com.springFramework.mm.enums.ErrorCode;

public class ServerException extends RuntimeException {
    private final ErrorCode errorCode;

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
