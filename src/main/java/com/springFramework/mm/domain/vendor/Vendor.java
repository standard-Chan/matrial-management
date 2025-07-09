package com.springframework.mm.domain.vendor;

import com.springframework.mm.enums.VendorGroupCode;
import com.springframework.mm.util.VendorGroupCodeConverter;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 구매처 이름

    private String businessRegistrationNo; // 사업자 번호

    private String personalId; // 개인 번호

    @Convert(converter = VendorGroupCodeConverter.class)
    private VendorGroupCode vendorGroupCode; // 구매처 그룹

    private String countryCode; // 구매처 그룹

    private String address; // 주소

    @Version
    private Long version; // 낙관적 락 전용 필드
}
