package com.springFramework.mm.exception.vendor;

import com.springFramework.mm.enums.ErrorCode;
import com.springFramework.mm.exception.ServerException;

public class VendorCompanyException extends ServerException {
    public VendorCompanyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
