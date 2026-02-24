package com.optivem.eshop.systemtest.core.gherkin.port;

public interface WhenCancelOrderPort {
    WhenCancelOrderPort withOrderNumber(String orderNumber);

    ThenPort then();
}
