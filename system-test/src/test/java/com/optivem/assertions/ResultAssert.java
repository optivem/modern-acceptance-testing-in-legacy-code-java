package com.optivem.assertions;

import com.optivem.results.Result;
import org.assertj.core.api.AbstractAssert;

public class ResultAssert extends AbstractAssert<ResultAssert, Result<?>> {

    private ResultAssert(Result<?> actual) {
        super(actual, ResultAssert.class);
    }

    public static ResultAssert assertThatResult(Result<?> actual) {
        return new ResultAssert(actual);
    }

    @SuppressWarnings("UnusedReturnValue")
    public ResultAssert isSuccess() {
        isNotNull();
        if (!actual.isSuccess()) {
            failWithMessage("Expected result to be success but was failure with errors: %s", actual.getErrors());
        }
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public ResultAssert isFailure() {
        isNotNull();
        if (!actual.isFailure()) {
            failWithMessage("Expected result to be failure but was success");
        }
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public ResultAssert isFailure(String errorMessage) {
        isFailure();
        if (!actual.getErrors().contains(errorMessage)) {
            failWithMessage("Expected result to contain error '%s' but errors were: %s", errorMessage, actual.getErrors());
        }
        return this;
    }
}

