package com.springframework.mm.service;

import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderHeaderCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemCreationRequest;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.vendor.VendorException;
import com.springframework.mm.repository.MaterialRepository;
import com.springframework.mm.repository.StorageRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderHeaderRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderItemRepository;
import com.springframework.mm.repository.vendor.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final VendorRepository vendorRepository;
    private final PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;
    private final MaterialRepository materialRepository;
    private final StorageRepository storageRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    /** 구매오더 헤더와 품목을 동시에 INSERT */
    @Transactional
    public void createPurchaseOrder(PurchaseOrderCreationRequest request) {
        PurchaseOrderHeaderCreationRequest headerRequest = request.getPurchaseOrderHeader();
        List<PurchaseOrderItemCreationRequest> itemsRequest = request.getItems();

        // 헤더에 저장할 구매처 데이터 가져오기
        Vendor vendor = vendorRepository.getVendorById(headerRequest.getVendorId())
                .orElseThrow(() -> new VendorException(ErrorCode.NOT_FOUND_VENDOR));
        PurchaseOrderHeader header = headerRequest.toEntity(vendor);
        purchaseOrderHeaderRepository.save(header);
        purchaseOrderHeaderRepository.flush(); // insert DB 반영

        // 추후에 bulk 연산으로 바꿔서 처리.

        itemsRequest.stream().map(itemRequest -> {
            return createPurchaseOrderItem(itemRequest, header);
        });
    }

    @Transactional
    protected PurchaseOrderItem createPurchaseOrderItem(PurchaseOrderItemCreationRequest request, PurchaseOrderHeader purchaseOrderHeader) {
        // 수량이 올바른지 확인
        request.checkQuantity();

        // 연관 데이터 가져오기
        Material material = materialRepository.getMaterialById(request.getMaterialId()).orElseThrow(() -> new EntityNotFoundException());
        Storage storage = storageRepository.getStorageById(request.getStorageId()).orElseThrow(() -> new EntityNotFoundException());

        return purchaseOrderItemRepository.save(request.toEntity(purchaseOrderHeader, material, storage));
    }
}
