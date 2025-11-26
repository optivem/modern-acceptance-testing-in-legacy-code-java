package com.optivem.eshop.systemtest.core.commons.results;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

public class Result<T> {
    @Getter
    private final boolean success;
    private final T value;
    private final Collection<String> errors;

    private Result(boolean success, T value, Collection<String> errors) {
        this.success = success;
        this.value = value;
        this.errors = errors;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(true, value, null);
    }

    public static <T> Result<T> failure(Collection<String> errors) {
        return new Result<>(false, null, errors);
    }

    public static Result<Void> success() {
        return new Result<>(true, null, null);
    }

    public static Result<Void> failure() {
        return failure(new ArrayList<>());
    }

    public boolean isFailure() {
        return !success;
    }

    public T getValue() {
        if (!success) {
            throw new IllegalStateException("Cannot get value from a failed result");
        }
        return value;
    }

    public Collection<String> getErrors() {
        if (success) {
            throw new IllegalStateException("Cannot get error from a successful result");
        }
        return errors;
    }
}
