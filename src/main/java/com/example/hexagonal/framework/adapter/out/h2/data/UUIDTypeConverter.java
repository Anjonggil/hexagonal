package com.example.hexagonal.framework.adapter.out.h2.data;


import javax.persistence.AttributeConverter;
import java.util.UUID;

public class UUIDTypeConverter implements AttributeConverter<UUID,String> {
    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        return (attribute ==null) ? null : attribute.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : UUID.fromString(dbData);
    }
}
