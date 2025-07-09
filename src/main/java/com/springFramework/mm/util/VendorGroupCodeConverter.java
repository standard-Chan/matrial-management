package com.springframework.mm.util;

import com.springframework.mm.enums.VendorGroupCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VendorGroupCodeConverter implements AttributeConverter<VendorGroupCode, String> {

    @Override
    public String convertToDatabaseColumn(VendorGroupCode attribute) {
        if (attribute == null) return null;
        return attribute.getCode(); // DB에 저장할 값
    }

    @Override
    public VendorGroupCode convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return VendorGroupCode.fromCode(dbData); // DB에서 읽은 값 → enum
    }
}
