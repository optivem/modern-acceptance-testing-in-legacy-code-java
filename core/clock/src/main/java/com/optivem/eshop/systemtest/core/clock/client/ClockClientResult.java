package com.optivem.eshop.systemtest.core.clock.client;

import com.optivem.commons.util.Result;
import com.optivem.eshop.systemtest.core.clock.client.dtos.error.ExtClockErrorResponse;

import java.util.function.Function;

public class ClockClientResult<T> {

    private final Result<T, ExtClockErrorResponse> result;

    private ClockClientResult(Result<T, ExtClockErrorResponse> result) {
        this.result = result;
    }

    public static <T> ClockClientResult<T> success(T value) {
        return new ClockClientResult<>(Result.success(value));
    }

    public static ClockClientResult<Void> success() {
        return new ClockClientResult<>(Result.success());
    }

    public static <T> ClockClientResult<T> failure(ExtClockErrorResponse error) {
        return new ClockClientResult<>(Result.failure(error));
    }

    public static <T> ClockClientResult<T> from(Result<T, ExtClockErrorResponse> result) {
        return new ClockClientResult<>(result);
    }

    public <U> ClockClientResult<U> map(Function<T, U> mapper) {
        return new ClockClientResult<>(result.map(mapper));
    }

    public <E> Result<T, E> mapError(Function<ExtClockErrorResponse, E> mapper) {
        return result.mapError(mapper);
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

    public ExtClockErrorResponse getError() {
        return result.getError();
    }

    public Result<T, ExtClockErrorResponse> toResult() {
        return result;
    }
}
