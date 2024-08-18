package com.shinhan.solsolhigh.user.domain;


import jakarta.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        return attribute.getType().getValue();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return Gender.of(dbData);
    }
}
