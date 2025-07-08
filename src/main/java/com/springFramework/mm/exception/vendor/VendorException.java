package com.springFramework.mm.exception.vendor;

import com.springFramework.mm.enums.ErrorCode;
import com.springFramework.mm.exception.ServerException;

public class VendorException extends ServerException {
    public VendorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
