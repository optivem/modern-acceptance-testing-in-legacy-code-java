package com.optivem.eshop.systemtest.dsl.api.then;

public interface ThenPort {
    ThenSuccessPort shouldSucceed();

    ThenFailurePort shouldFail();
}
