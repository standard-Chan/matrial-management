package com.springFramework.mm.exception.vendor;

import com.springFramework.mm.enums.ErrorCode;
import com.springFramework.mm.exception.ServerException;

public class VendorPurchasingException extends ServerException {
    public VendorPurchasingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
