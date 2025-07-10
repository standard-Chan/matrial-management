package optimisitcLock;

import com.springframework.mm.MaterialManagerApplication;
import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.domain.vendor.VendorPurchasing;
import com.springframework.mm.dto.vendor.PurchasingUpdateRequest;
import com.springframework.mm.enums.ErrorCode;
import com.springframework.mm.enums.TaxCode;
import com.springframework.mm.enums.VendorGroupCode;
import com.springframework.mm.exception.vendor.VendorException;
import com.springframework.mm.repository.vendor.VendorPurchasingRepository;
import com.springframework.mm.repository.vendor.VendorRepository;
import com.springframework.mm.service.vendor.VendorPurchasingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = MaterialManagerApplication.class)
public class VendorPurchasingOptimisticLockConcurrencyTest {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorPurchasingRepository purchasingRepository;

    @Autowired
    private VendorPurchasingService purchasingService;

    private Long purchasingId;

    @BeforeEach
    void setUp() {
        Vendor vendor = vendorRepository.save(
                Vendor.builder()
                        .name("동시성 테스트 공급처")
                        .countryCode("KR")
                        .vendorGroupCode(VendorGroupCode.DOMESTIC)
                        .personalId("850101-1234567")
                        .businessRegistrationNo("1650212345")
                        .address("서울시 강남구")
                        .build()
        );

        VendorPurchasing purchasing = purchasingRepository.save(
                VendorPurchasing.builder()
                        .vendor(vendor)
                        .purchasingOrgCode("ORG1")
                        .purchasingGroupCode("GRP1")
                        .currency("KRW")
                        .taxCode(TaxCode.V1)
                        .build()
        );

        purchasingId = purchasing.getId();
    }

    @Test
    void 동시_수정_낙관적락_충돌_테스트() throws InterruptedException, ExecutionException {
        // Thread pool
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 결과 저장용 Future
        Future<String> result1 = executor.submit(() -> tryUpdate("ORG_A", 200));
        Future<String> result2 = executor.submit(() -> tryUpdate("ORG_B", 0));

        int conflictCount = 0;
        for (Future<String> result : new Future[]{result1, result2}) {
            try {
                String r = result.get();
                System.out.println("✅ 성공: " + r);
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof VendorException ve &&
                        ve.getErrorCode() == ErrorCode.CONFLICT_OPTIMISTIC_LOCK) {
                    conflictCount++;
                    System.out.println("⚠️ 충돌 발생: " + ve.getMessage());
                } else {
                    throw e;
                }
            }
        }

        // 하나는 성공, 하나는 충돌했는지 확인
        assertThat(conflictCount).isEqualTo(1);
        executor.shutdown();
    }

    private String tryUpdate(String orgCode, long delayMillis) {
        try {
            // delay 주기 (충돌 유도용)
            Thread.sleep(delayMillis);

            PurchasingUpdateRequest request = PurchasingUpdateRequest.builder()
                    .id(purchasingId)
                    .purchasingOrgCode(orgCode)
                    .purchasingGroupCode("GRP")
                    .currency("USD")
                    .taxCode(TaxCode.V2)
                    .build();

            purchasingService.updatePurchasing(request);
            return orgCode;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
