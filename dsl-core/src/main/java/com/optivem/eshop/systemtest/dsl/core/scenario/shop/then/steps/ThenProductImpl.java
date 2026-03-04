package com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps;

import com.optivem.eshop.systemtest.dsl.core.app.erp.usecases.GetProductVerification;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenProduct;

public class ThenProductImpl implements ThenProduct {
    private final GetProductVerification verification;

    public ThenProductImpl(GetProductVerification verification) {
        this.verification = verification;
    }

    @Override
    public ThenProduct hasSku(String sku) {
        verification.sku(sku);
        return this;
    }

    @Override
    public ThenProduct hasPrice(double price) {
        verification.price(price);
        return this;
    }
}
