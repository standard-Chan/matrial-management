package com.springframework.mm.exception.vendor;

import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.ServerException;

public class VendorCompanyException extends ServerException {
    public VendorCompanyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
