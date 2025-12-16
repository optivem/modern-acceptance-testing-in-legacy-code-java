package com.optivem.eshop.systemtest.core.commons.error;

import com.optivem.lang.Result;

public final class Results {
    private Results() {
        // Utility class
    }

    public static <T> Result<T, Error> success(T value) {
        return Result.success(value);
    }

    public static Result<Void, Error> success() {
        return Result.success();
    }

    public static <T> Result<T, Error> failure(String message) {
        return Result.failure(Error.of(message));
    }

    public static <T> Result<T, Error> failure(String message, Error.FieldError... fieldErrors) {
        return Result.failure(Error.of(message, fieldErrors));
    }

    public static <T> Result<T, Error> failure(Error error) {
        return Result.failure(error);
    }
}
