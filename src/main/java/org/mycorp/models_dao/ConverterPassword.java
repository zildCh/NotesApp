package org.mycorp.models_dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;

@Converter
public class ConverterPassword implements AttributeConverter<String, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(String attribute) {
        return attribute.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String convertToEntityAttribute(byte[] dbData) {
        return dbData.toString();
    }
}
