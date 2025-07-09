package com.springframework.mm.dto;

import com.springframework.mm.domain.Material;
import com.springframework.mm.enums.QuantityUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MaterialCreationRequest {
    private String name;
    private QuantityUnit baseUnit;
    private Long price;

    public Material toEntity() {
        return Material.builder()
                .name(this.name)
                .baseUnit(this.baseUnit)
                .price(this.price)
                .build();
    }
}
