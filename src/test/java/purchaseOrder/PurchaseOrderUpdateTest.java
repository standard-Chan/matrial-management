package purchaseOrder;


import com.springframework.mm.domain.Material;
import com.springframework.mm.domain.Storage;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import com.springframework.mm.dto.purchaseOrder.PurchaseOrderItemUpdateRequest;
import com.springframework.mm.exception.MaterialException;
import com.springframework.mm.exception.StorageException;
import com.springframework.mm.exception.purchaseOrder.PurchaseOrderItemException;
import com.springframework.mm.repository.MaterialRepository;
import com.springframework.mm.repository.StorageRepository;
import com.springframework.mm.repository.purchasingOrder.PurchaseOrderItemRepository;
import com.springframework.mm.service.PurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PurchaseOrderUpdateTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private PurchaseOrderItemRepository itemRepository;

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private PurchaseOrderService purchaseOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 구매_오더_품목_수정() {
        Long materialId = 1L;
        Long itemId = 2L;
        Long storageId = 3L;

        Material material = Material.builder().id(materialId).version(0L).name("철근").build();
        Storage storage = Storage.builder().id(storageId).version(0L).name("창고 A").build();
        PurchaseOrderItem item = PurchaseOrderItem.builder()
                .id(itemId)
                .version(0L)
                .quantity(10L)
                .build();

        PurchaseOrderItemUpdateRequest request = new PurchaseOrderItemUpdateRequest();
        request.setId(itemId);
        request.setMaterialId(materialId);
        request.setStorageId(storageId);
        request.setQuantity(20L);
        request.setDeliveryDate(LocalDate.of(2025, 7, 20));

        when(materialRepository.findByIdWithOptimisticLock(materialId)).thenReturn(Optional.of(material));
        when(storageRepository.findByIdWithOptimisticLock(storageId)).thenReturn(Optional.of(storage));
        when(itemRepository.findByIdWithOptimisticLock(itemId)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(PurchaseOrderItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderItem result = purchaseOrderService.updatePurchaseOrderItem(request);

        assertThat(result.getQuantity()).isEqualTo(20L);
        assertThat(result.getDeliveryDate()).isEqualTo(LocalDate.of(2025, 7, 20));
        assertThat(result.getMaterial()).isEqualTo(material);
        assertThat(result.getStorage()).isEqualTo(storage);
    }

//    @Test
//    void updatePurchaseOrderItem_materialNotFound() {
//        Long itemId = 2L;
//        Long materialId = 999L;
//        Long storageId = 3L;
//
//        PurchaseOrderItemUpdateRequest request = new PurchaseOrderItemUpdateRequest();
//        request.setId(itemId);
//        request.setMaterialId(materialId);
//        request.setStorageId(storageId);
//
//        when(materialRepository.findByIdWithOptimisticLock(materialId)).thenReturn(Optional.empty());
//
//        assertThrows(MaterialException.class, () -> purchaseOrderService.updatePurchaseOrderItem(request));
//    }

//    @Test
//    void updatePurchaseOrderItem_storageNotFound() {
//        Long itemId = 2L;
//        Long materialId = 1L;
//        Long storageId = 999L;
//
//        Material material = Material.builder().id(materialId).version(0L).build();
//        when(materialRepository.findByIdWithOptimisticLock(materialId)).thenReturn(Optional.of(material));
//        when(storageRepository.findByIdWithOptimisticLock(storageId)).thenReturn(Optional.empty());
//
//        PurchaseOrderItemUpdateRequest request = new PurchaseOrderItemUpdateRequest();
//        request.setId(itemId);
//        request.setMaterialId(materialId);
//        request.setStorageId(storageId);
//
//        assertThrows(StorageException.class, () -> purchaseOrderService.updatePurchaseOrderItem(request));
//    }

    @Test
    void updatePurchaseOrderItem_itemNotFound() {
        Long itemId = 999L;
        Long materialId = 1L;
        Long storageId = 2L;

        Material material = Material.builder().id(materialId).version(0L).build();
        Storage storage = Storage.builder().id(storageId).version(0L).build();

        when(materialRepository.findByIdWithOptimisticLock(materialId)).thenReturn(Optional.of(material));
        when(storageRepository.findByIdWithOptimisticLock(storageId)).thenReturn(Optional.of(storage));
        when(itemRepository.findByIdWithOptimisticLock(itemId)).thenReturn(Optional.empty());

        PurchaseOrderItemUpdateRequest request = new PurchaseOrderItemUpdateRequest();
        request.setId(itemId);
        request.setMaterialId(materialId);
        request.setStorageId(storageId);

        assertThrows(PurchaseOrderItemException.class, () -> purchaseOrderService.updatePurchaseOrderItem(request));
    }

    // 낙관적 락 충돌 테스트는 실제 DB를 사용하는 통합 테스트로 별도 작성 필요
}
