package com.springframework.mm.service;

import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import com.springframework.mm.domain.vendor.VendorCompany;
import com.springframework.mm.dto.common.IdRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderHeaderCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemCreationRequest;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemUpdateRequest;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.exception.MaterialException;
import com.springframework.mm.exception.StorageException;
import com.springframework.mm.exception.purchaseOrder.PurchaseOrderItemException;
import com.springframework.mm.exception.vendor.VendorCompanyException;
import com.springframework.mm.repository.MaterialRepository;
import com.springframework.mm.repository.StorageRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderHeaderRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderItemRepository;
import com.springframework.mm.repository.vendor.VendorCompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderHeaderRepository headerRepository;
    private final PurchaseOrderItemRepository itemRepository;
    private final MaterialRepository materialRepository;
    private final StorageRepository storageRepository;
    private final VendorCompanyRepository vendorCompanyRepository;

    /** 구매오더 헤더와 품목을 동시에 INSERT */
    @Transactional
    public void createPurchaseOrder(PurchaseOrderCreationRequest request) {
        log.info("구매 오더 생성 요청 수신 - 구매 오더 헤더 회사번호: {}, 품목 개수: {}",
                request.getPurchaseOrderHeader().getVendorCompanyId(),
                request.getItems().size());

        PurchaseOrderHeaderCreationRequest headerRequest = request.getPurchaseOrderHeader();
        List<PurchaseOrderItemCreationRequest> itemsRequest = request.getItems();

        // item 값이 비어있는 경우
        if (itemsRequest.isEmpty()) {
            log.warn("잘못된 요청 값 - 품목이 비어 있음 {}", ErrorCode.INVALID_PAYLOAD);
            throw new PurchaseOrderItemException(ErrorCode.INVALID_PAYLOAD);
        }

        // 헤더에 저장할 구매처 회사 데이터 가져오기
        log.debug("구매 오더 헤더 저장 시도 - vendorCompany: {}", headerRequest.getVendorCompanyId());
        VendorCompany vendorCompany = vendorCompanyRepository.getVendorCompanyById(headerRequest.getVendorCompanyId())
                .orElseThrow(() -> new VendorCompanyException(ErrorCode.NOT_FOUND_COMPANY));
        PurchaseOrderHeader header = headerRequest.toEntity(vendorCompany);
        headerRepository.save(header);
        headerRepository.flush(); // insert DB 반영
        log.debug("구매 오더 헤더 저장 완료 - id: {}", header.getId());


        // 추후에 bulk 연산으로 바꿔서 처리.

        log.debug("{}개의 품목 저장 시도", itemsRequest.size());
        itemsRequest.forEach(itemRequest -> {
            createPurchaseOrderItem(itemRequest, header);
        });
        log.debug("{}개 품목 저장 완료", itemsRequest.size());
    }

    @Transactional
    protected void createPurchaseOrderItem(PurchaseOrderItemCreationRequest request, PurchaseOrderHeader purchaseOrderHeader) {

        // 수량이 올바른지 확인
        request.checkQuantity();

        // 연관 데이터 가져오기
        Material material = materialRepository.getMaterialById(request.getMaterialId()).orElseThrow(() -> new EntityNotFoundException());
        Storage storage = storageRepository.getStorageById(request.getStorageId()).orElseThrow(() -> new EntityNotFoundException());

        itemRepository.save(request.toEntity(purchaseOrderHeader, material, storage));
    }

    public List<PurchaseOrderHeader> getAllHeaders() {
        log.info("전체 구매 오더 헤더 조회 요청 수신");
        List<PurchaseOrderHeader> result = headerRepository.findAllWithCompanyAndVendor();
        log.debug("조회된 헤더 개수: {}", result.size());
        return result;
    }

    public List<PurchaseOrderItem> getAllItems() {
        log.info("전체 구매 오더 품목 조회 요청 수신");
        List<PurchaseOrderItem> result = itemRepository.findAllWithJoins();
        log.debug("조회된 품목 개수: {}", result.size());
        return result;
    }

    @Transactional
    public List<PurchaseOrderItem> updatePurchaseOrderItems(List<PurchaseOrderItemUpdateRequest> requests) {
        log.info("구매 오더 품목 '{}'개 수정 요청 수신.", requests.size());

        return requests.stream().map(request -> {
            try {
                return updatePurchaseOrderItem(request);
            } catch (OptimisticLockingFailureException e) {
                log.warn("구매 오더 품목 수정 실패 - 낙관적 락 충돌 발생: 구매 오더 Id: {}", request.getId());
                throw new PurchaseOrderItemException(ErrorCode.CONFLICT_OPTIMISTIC_LOCK);
            }
        }).toList();
    }


    @Transactional
    /** 낙관적 락을 사용하여 데이터 가져오기 & 업데이터 */
    public PurchaseOrderItem updatePurchaseOrderItem(PurchaseOrderItemUpdateRequest request) {
        Material material = materialRepository.findByIdWithOptimisticLock(request.getMaterialId())
                .orElseThrow(() -> new MaterialException(ErrorCode.NOT_FOUND_MATERIAL));
        PurchaseOrderItem item = itemRepository.findByIdWithOptimisticLock(request.getId())
                .orElseThrow(() -> new PurchaseOrderItemException(ErrorCode.NOT_FOUND_PURCHASE_ORDER_ITEM));
        Storage storage = storageRepository.findByIdWithOptimisticLock(request.getStorageId())
                .orElseThrow(() -> new StorageException(ErrorCode.NOT_FOUND_STORAGE));

        item.setQuantity(request.getQuantity());
        item.setDeliveryDate(request.getDeliveryDate());
        item.setMaterial(material);
        item.setStorage(storage);

        return itemRepository.save(item);
    }

    @Transactional
    public void deletePurchaseOrderItems(List<IdRequest> requests) {
        List<Long> ids = requests.stream()
                .map(IdRequest::getId)
                .toList();

        itemRepository.deleteAllById(ids);
    }
}
