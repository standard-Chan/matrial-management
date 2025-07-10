package com.springframework.mm.exception;

import com.springframework.mm.enums.ErrorCode;

public class StorageException extends ServerException {
    public StorageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
