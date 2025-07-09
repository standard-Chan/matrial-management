package com.springframework.mm.exception.vendor;

import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.ServerException;

public class VendorException extends ServerException {
    public VendorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
