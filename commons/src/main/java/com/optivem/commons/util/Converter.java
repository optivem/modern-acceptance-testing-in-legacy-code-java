package com.optivem.commons.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.function.Function;

public class Converter {
    public static BigDecimal toBigDecimal(String value) {
        return from(value, BigDecimal::new);
    }

    public static BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }

    public static String fromBigDecimal(BigDecimal value) {
        return from(value, BigDecimal::toString);
    }

    public static String fromDouble(double value) {
        return BigDecimal.valueOf(value).toString();
    }

    public static Integer toInteger(String value) {
        return to(value, Integer::parseInt);
    }

    public static String fromInteger(Integer value) {
        return from(value, Object::toString);
    }

    public static Double toDouble(String value) {
        return to(value, Double::parseDouble);
    }

    public static Instant toInstant(String value) {
        return to(value, Instant::parse);
    }

    public static String fromInstant(Instant value) {
        return from(value, Instant::toString);
    }

    private static <T, R> R from(T value, Function<T, R> converter) {
        return value == null ? null : converter.apply(value);
    }

    private static <T> T to(String value, Function<String, T> converter) {
        return (value == null || value.isEmpty()) ? null : converter.apply(value);
    }
}
