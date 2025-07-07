package vendor;

import com.springFramework.mm.MaterialManagerApplication;
import com.springFramework.mm.domain.Vendor;
import com.springFramework.mm.dto.vendor.VendorCreationRequest;
import com.springFramework.mm.repository.VendorRepository;
import com.springFramework.mm.service.VendorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MaterialManagerApplication.class)
@ActiveProfiles("test")
public class VendorServiceTest {

    @Autowired
    private VendorService vendorService;
    @Autowired
    private VendorRepository vendorRepository;

    @AfterEach
    void tearDown() {
        vendorRepository.deleteAll();
    }

    @Test
    void 구매처를_정상적으로_등록한다() {
        // given
        VendorCreationRequest request = VendorCreationRequest.builder()
                .name("신세계푸드")
                .countryCode("KR")
                .vendorGroupCode("1000")
                .personalId("850101-1234567")
                .businessRegistrationNo("1650212345")
                .address("서울시 서초구 양재동")
                .build();

        // when
        vendorService.createVendor(request);

        // then
        List<Vendor> all = vendorRepository.findAll();
        assertThat(all).hasSize(1);

        Vendor vendor = all.get(0);
        assertThat(vendor.getName()).isEqualTo("신세계푸드");
    }
}
