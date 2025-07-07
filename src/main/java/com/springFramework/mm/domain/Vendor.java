package com.springFramework.mm.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Builder
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String vendorCode; // 구매처 번호

    private String name; // 구매처 이름

    private String businessRegistrationNo; // 사업자 번호

    private String personalId; // 개인 번호

    private String vendorGroupCode; // 구매처 그룹

    private String countryCode; // 구매처 그룹

    private String address; // 주소
}
