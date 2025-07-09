package com.springframework.mm.exception.vendor;

import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.ServerException;

public class VendorPurchasingException extends ServerException {
    public VendorPurchasingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
