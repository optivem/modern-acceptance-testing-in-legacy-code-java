package com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps;

import com.optivem.eshop.systemtest.dsl.core.app.erp.usecases.GetProductVerification;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenErp;

public class ThenErpImpl implements ThenErp {
    private final GetProductVerification verification;

    public ThenErpImpl(GetProductVerification verification) {
        this.verification = verification;
    }

    @Override
    public ThenErp hasSku(String sku) {
        verification.sku(sku);
        return this;
    }

    @Override
    public ThenErp hasPrice(double price) {
        verification.price(price);
        return this;
    }
}
