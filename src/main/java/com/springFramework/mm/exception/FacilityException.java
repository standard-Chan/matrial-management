package com.springframework.mm.exception;

import com.springframework.mm.enums.ErrorCode;

public class FacilityException extends ServerException {
    public FacilityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
