package com.optivem.eshop.systemtest.dsl.api;

public interface WhenViewOrderPort {
    WhenViewOrderPort withOrderNumber(String orderNumber);

    ThenPort then();
}
