package com.springframework.mm.exception.vendorOrder;

import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.ServerException;

public class VendorOrderItemException extends ServerException {
    public VendorOrderItemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
