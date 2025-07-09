package optimisitcLock;

import com.springframework.mm.MaterialManagerApplication;
import com.springframework.mm.domain.vendor.Vendor;
import com.springframework.mm.dto.vendor.VendorCreationRequest;
import com.springframework.mm.dto.vendor.VendorUpdateRequest;
import com.springframework.mm.repository.vendor.VendorRepository;
import com.springframework.mm.service.VendorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import static com.springframework.mm.enums.VendorGroupCode.DOMESTIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = MaterialManagerApplication.class)
@Transactional
class VendorOptimisticLockTest {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorRepository vendorRepository;

    @PersistenceContext
    private EntityManager em;

    @AfterEach
    void 종료() {
        vendorRepository.deleteAll();
    }

    @Test
    void 낙관적락_충돌_테스트() {
        // given
        Vendor v1 = vendorService.createVendor(createVendorRequest());

        // em.clear()로 1차 캐시 초기화
        em.flush();
        em.clear();

        // when   v1에 대한 충돌 테스트
        // A 요청 및 영속성 컨텍스트 분리
        Vendor A_vendor1 = vendorRepository.findVendorById(v1.getId());
        em.detach(A_vendor1);
        // B 요청 및 영속성 컨텍스트 분리
        Vendor B_vendor1 = vendorRepository.findVendorById(v1.getId());
        em.detach(B_vendor1);
        // A가 수정 및 저장
        A_vendor1.setName("A");
        Vendor av = vendorRepository.save(A_vendor1);
        em.flush(); // 버전 증가됨

        // B도 수정 시도 → 낙관적 락 예외 발생 예상
        B_vendor1.setName("B");
        assertThrows(OptimisticLockingFailureException.class, () -> {
            Vendor bv = vendorRepository.save(B_vendor1);
            em.flush();
        });

        // 먼저 수정한 데이터는 그대로
        assertThat(vendorRepository.getVendorById(v1.getId()).get().getName()).isEqualTo("A");
    }

    private VendorUpdateRequest createUpdateRequest(Long id, String name){
        return VendorUpdateRequest.builder()
                .id(id)
                .name(name)
                .countryCode("KR")
                .vendorGroupCode(DOMESTIC)
                .personalId("updated:850101-1234567")
                .businessRegistrationNo("updated:1650212345")
                .address("서울시 강남구2")
                .build();
    }

    private VendorCreationRequest createVendorRequest() {
        return VendorCreationRequest.builder()
                .name("락 충돌 테스트 기업")
                .countryCode("KR")
                .vendorGroupCode(DOMESTIC)
                .personalId("850101-1234567")
                .businessRegistrationNo("1650212345")
                .address("서울시 강남구")
                .build();
    }
}
