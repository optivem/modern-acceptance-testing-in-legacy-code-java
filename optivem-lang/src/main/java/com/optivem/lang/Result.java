package com.optivem.lang;

import lombok.Getter;

public class Result<T, E> {
    @Getter
    private final boolean success;
    private final T value;
    private final E error;

    private Result(boolean success, T value, E error) {
        this.success = success;
        this.value = value;
        this.error = error;
    }

    public static <T, E> Result<T, E> success(T value) {
        return new Result<>(true, value, null);
    }

    public static <T, E> Result<T, E> failure(E error) {
        return new Result<>(false, null, error);
    }

    public static <E> Result<Void, E> success() {
        return new Result<>(true, null, null);
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

    public E getError() {
        if (success) {
            throw new IllegalStateException("Cannot get error from a successful result");
        }
        return error;
    }
}

