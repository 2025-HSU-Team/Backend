package com.sosaw.sosaw.global.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

import java.util.Arrays;

@Converter(autoApply = false)
public class FloatArrayVectorConverter implements AttributeConverter<float[], Object> {
    //TODO: 예외처리 리팩토링 필요

    // DB저장 Attribute -> DB
    @Override
    public Object convertToDatabaseColumn(float[] attribute) {
        System.out.println(">>> convertToDatabaseColumn len=" + (attribute==null? "null": attribute.length));

        if (attribute == null) return null;
        try {
            PGobject pg = new PGobject();
            pg.setType("vector");               // 반드시 "vector"
            pg.setValue(toLiteral(attribute));  // 예: [0.1, -0.2, ...]
            return pg;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert float[] to pgvector", e);
        }
    }

    @Override
    public float[] convertToEntityAttribute(Object dbData) {
        if (dbData == null) return null;
        String val = (dbData instanceof PGobject) ? ((PGobject) dbData).getValue()
                : dbData.toString();
        return parseLiteral(val);
    }

    private static String toLiteral(float[] a) {
        StringBuilder sb = new StringBuilder(a.length * 8 + 2);
        sb.append('[');
        for (int i = 0; i < a.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(Float.toString(a[i]));
        }
        sb.append(']');
        return sb.toString();
    }

    private static float[] parseLiteral(String s) {
        String trimmed = s.trim();
        if (trimmed.length() < 2 || trimmed.charAt(0) != '[' || trimmed.charAt(trimmed.length()-1) != ']')
            throw new IllegalArgumentException("Invalid pgvector literal: " + s);
        String inner = trimmed.substring(1, trimmed.length()-1).trim();
        if (inner.isEmpty()) return new float[0];
        String[] parts = inner.split(",");
        float[] out = new float[parts.length];
        for (int i = 0; i < parts.length; i++) {
            out[i] = Float.parseFloat(parts[i].trim());
        }
        return out;
    }
}

