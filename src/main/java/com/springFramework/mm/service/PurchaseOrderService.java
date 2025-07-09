package com.springframework.mm.service;

import com.springframework.mm.repository.purchasingOrder.PurchaseOrderHeaderRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private PurchaseOrderHeaderRepository headerRepository;
    private PurchaseOrderItemRepository itemRepository;

}
