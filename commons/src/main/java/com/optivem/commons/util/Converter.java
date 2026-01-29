package com.optivem.commons.util;

import java.math.BigDecimal;
import java.time.Instant;

public class Converter {
    public static BigDecimal toBigDecimal(String value) {
        if (value == null) {
            return null;
        }
        return new BigDecimal(value);
    }

    public static BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }

    public static String fromBigDecimal(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static String fromDouble(double value) {
        return BigDecimal.valueOf(value).toString();
    }

    public static Integer toInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }

    public static String fromInteger(Integer value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static Double toDouble(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Double.parseDouble(value);
    }

    public static Instant toInstant(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Instant.parse(value);
    }

    public static String fromInstant(Instant value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
