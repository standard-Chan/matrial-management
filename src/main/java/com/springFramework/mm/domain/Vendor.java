package com.springFramework.mm.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    private String vendorGroupCode; // 구매처 그룹

    private String countryCode; // 구매처 그룹

    private String address; // 주소
}
