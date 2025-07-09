package com.springframework.mm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MaterialRequestListWrapper {
    private List<MaterialCreationRequest> materials;
}

