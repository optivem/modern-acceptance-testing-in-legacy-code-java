package com.optivem.eshop.systemtest.dsl.api;

public interface WhenCancelOrderPort {
    WhenCancelOrderPort withOrderNumber(String orderNumber);

    ThenPort then();
}
