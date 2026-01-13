package com.optivem.lang;

import java.math.BigDecimal;

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
}
