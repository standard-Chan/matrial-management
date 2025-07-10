package com.springframework.mm.exception;

import com.springframework.mm.enums.ErrorCode;

public class MaterialException extends ServerException {
    public MaterialException(ErrorCode errorCode) {
        super(errorCode);
    }
}
