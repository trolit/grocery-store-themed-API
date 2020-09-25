package com.trolit.github.grocerystore.converters;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

public class StringConverters {
    public static Converter<String, String> basicPropertyConverter() {
        return new AbstractConverter<>() {
            protected String convert(String source) {
                return source == null ? null : source.trim();
            }
        };
    }
}
