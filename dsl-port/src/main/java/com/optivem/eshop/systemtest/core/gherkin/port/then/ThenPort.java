package com.optivem.eshop.systemtest.dsl.api;

public interface ThenPort {
    ThenSuccessPort shouldSucceed();

    ThenFailurePort shouldFail();
}
