package com.optivem.eshop.systemtest.dsl.port.shop.then.steps;

public interface ThenProduct {
    ThenProduct hasSku(String sku);

    ThenProduct hasPrice(double price);
}
