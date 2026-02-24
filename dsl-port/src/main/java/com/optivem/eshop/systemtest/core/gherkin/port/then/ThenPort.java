package com.optivem.eshop.systemtest.core.gherkin.port;

public interface ThenPort {
    ThenSuccessPort shouldSucceed();

    ThenFailurePort shouldFail();
}
