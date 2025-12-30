package com.optivem.eshop.systemtest.core.shop.commons;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.error.SystemError;
import com.optivem.lang.Result;

public final class Results {
    private Results() {
        // Utility class
    }

    public static <T> Result<T, SystemError> success(T value) {
        return Result.success(value);
    }

    public static Result<Void, SystemError> success() {
        return Result.success();
    }

    public static <T> Result<T, SystemError> failure(String message) {
        return Result.failure(SystemError.of(message));
    }

    public static <T> Result<T, SystemError> failure(SystemError error) {
        return Result.failure(error);
    }
}
