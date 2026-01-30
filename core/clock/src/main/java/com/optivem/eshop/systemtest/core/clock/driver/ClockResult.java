package com.optivem.eshop.systemtest.core.clock.driver;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Result;

import java.util.function.Function;

public class ClockResult<T> {

    private final Result<T, ClockErrorResponse> result;

    private ClockResult(Result<T, ClockErrorResponse> result) {
        this.result = result;
    }

    public static <T> ClockResult<T> success(T value) {
        return new ClockResult<>(Result.success(value));
    }

    public static ClockResult<Void> success() {
        return new ClockResult<>(Result.success());
    }

    public static <T> ClockResult<T> failure(ClockErrorResponse error) {
        return new ClockResult<>(Result.failure(error));
    }

    public static <T> ClockResult<T> from(Result<T, ClockErrorResponse> result) {
        return new ClockResult<>(result);
    }

    public <U> ClockResult<U> map(Function<T, U> mapper) {
        return new ClockResult<>(result.map(mapper));
    }

    public boolean isSuccess() {
        return result.isSuccess();
    }

    public boolean isFailure() {
        return result.isFailure();
    }

    public T getValue() {
        return result.getValue();
    }

    public ClockErrorResponse getError() {
        return result.getError();
    }

    public Result<T, ClockErrorResponse> toResult() {
        return result;
    }
}
