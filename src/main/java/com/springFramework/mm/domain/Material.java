package com.springframework.mm.domain;

import com.springframework.mm.enums.QuantityUnit;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private QuantityUnit baseUnit; // 단위

    private Long price; // 단가

    @Version
    private Long version;
}
