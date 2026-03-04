package com.optivem.eshop.systemtest.dsl.port.scenario.then.steps;

public interface ThenProduct {
    ThenProduct hasSku(String sku);

    ThenProduct hasPrice(double price);
}
