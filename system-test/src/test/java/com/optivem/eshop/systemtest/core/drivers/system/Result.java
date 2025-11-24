package com.optivem.eshop.systemtest.core.drivers.system;

import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Result<T> {
    @Getter
    private final boolean success;
    private final T value;
    private final String error;

    private Result(boolean success, T value, String error) {
        this.success = success;
        this.value = value;
        this.error = error;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(true, value, null);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(false, null, error);
    }

    public static Result<Void> success() {
        return new Result<>(true, null, null);
    }

    public boolean isFailure() {
        return !success;
    }

    public T getValue() {
        if (!success) {
            throw new IllegalStateException("Cannot get value from a failed result. Error: " + error);
        }
        return value;
    }

    public String getError() {
        if (success) {
            throw new IllegalStateException("Cannot get error from a successful result");
        }
        return error;
    }

    @Override
    public String toString() {
        if (success) {
            return "Success(" + value + ")";
        } else {
            return "Failure(" + error + ")";
        }
    }
}
