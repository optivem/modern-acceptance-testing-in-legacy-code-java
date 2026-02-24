package com.optivem.eshop.systemtest.core.gherkin.port;

public interface WhenViewOrderPort {
    WhenViewOrderPort withOrderNumber(String orderNumber);

    ThenPort then();
}
