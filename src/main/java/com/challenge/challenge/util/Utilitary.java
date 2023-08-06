package com.challenge.challenge.util;

import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.ZoneId;

public class Utilitary {

    public static Object convertToFieldType(Class<?> fieldType, String fieldValue) {

        if(ObjectUtils.isEmpty(fieldValue))
            return null;

        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(fieldValue);
        } else if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(fieldValue);
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(fieldValue);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(fieldValue);
        }
        return fieldValue;
    }

    public static Object convertAvroValue(String fieldName, Object value) {

        if(ObjectUtils.isEmpty(value))
            return null;

        if ((fieldName.equals("tpep_pickup_datetime")|| fieldName.equals("tpep_dropoff_datetime"))&& value instanceof Long) {
            value = Instant.ofEpochMilli((Long) value / 1000)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }

        return value;
    }

}
