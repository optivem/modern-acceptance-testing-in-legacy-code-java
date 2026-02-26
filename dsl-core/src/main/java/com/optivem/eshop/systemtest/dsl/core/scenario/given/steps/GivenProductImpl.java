package com.optivem.eshop.systemtest.dsl.core.scenario.given.steps;

import com.optivem.common.util.Converter;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenProduct;

import static com.optivem.eshop.systemtest.dsl.core.scenario.ScenarioDefaults.*;

public class GivenProductImpl extends BaseGivenStep implements GivenProduct {
    private String sku;
    private String unitPrice;

    public GivenProductImpl(GivenImpl given) {
        super(given);
        withSku(DEFAULT_SKU);
        withUnitPrice(DEFAULT_UNIT_PRICE);
    }

    public GivenProductImpl withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public GivenProductImpl withUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public GivenProductImpl withUnitPrice(double unitPrice) {
        withUnitPrice(Converter.fromDouble(unitPrice));
        return this;
    }

    @Override
    public void execute(SystemDsl app) {
        app.erp().returnsProduct()
                .sku(sku)
                .unitPrice(unitPrice)
                .execute()
                .shouldSucceed();
    }
}

