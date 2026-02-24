package com.optivem.eshop.systemtest.core.gherkin.given.steps;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.core.system.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenProductPort;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenProductImpl extends BaseGivenStep implements GivenProductPort {
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
