package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.core.SystemDsl;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenProduct extends BaseGivenStep {
    private String sku;
    private String unitPrice;

    public GivenProduct(Given given) {
        super(given);
        withSku(DEFAULT_SKU);
        withUnitPrice(DEFAULT_UNIT_PRICE);
    }

    public GivenProduct withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public GivenProduct withUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public GivenProduct withUnitPrice(double unitPrice) {
        withUnitPrice(Converter.fromDouble(unitPrice));
        return this;
    }

    @Override
    void execute(SystemDsl app) {
        app.erp().returnsProduct()
                .sku(sku)
                .unitPrice(unitPrice)
                .execute()
                .shouldSucceed();
    }
}
