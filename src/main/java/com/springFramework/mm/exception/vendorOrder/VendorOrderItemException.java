package com.springFramework.mm.exception.vendorOrder;

import com.springFramework.mm.enums.ErrorCode;
import com.springFramework.mm.exception.ServerException;

public class VendorOrderItemException extends ServerException {
    public VendorOrderItemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
