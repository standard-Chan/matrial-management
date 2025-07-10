package com.springframework.mm.exception.purchaseOrder;

import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.ServerException;

public class PurchaseOrderItemException extends ServerException {
    public PurchaseOrderItemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
