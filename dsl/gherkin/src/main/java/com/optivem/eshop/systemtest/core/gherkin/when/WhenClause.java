package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class WhenClause {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private boolean hasProduct;
    private boolean hasTaxRate;

    public WhenClause(SystemDsl app, ScenarioDsl scenario) {
        this(app, scenario, false, false);
    }

    public WhenClause(SystemDsl app, ScenarioDsl scenario, boolean hasProduct, boolean hasTaxRate) {
        this.app = app;
        this.scenario = scenario;
        this.hasProduct = hasProduct;
        this.hasTaxRate = hasTaxRate;
    }

    private void ensureDefaults() {
        if (!hasProduct) {
            app.erp().returnsProduct()
                    .sku(DEFAULT_SKU)
                    .unitPrice(DEFAULT_UNIT_PRICE)
                    .execute()
                    .shouldSucceed();
            hasProduct = true;
        }

        if (!hasTaxRate) {
            app.tax().returnsTaxRate()
                    .country(DEFAULT_COUNTRY)
                    .taxRate(DEFAULT_TAX_RATE)
                    .execute()
                    .shouldSucceed();
            hasTaxRate = true;
        }
    }

    public PlaceOrderBuilder placeOrder() {
        ensureDefaults();
        return new PlaceOrderBuilder(app, scenario);
    }

    public CancelOrderBuilder cancelOrder() {
        ensureDefaults();
        return new CancelOrderBuilder(app, scenario);
    }

    public ViewOrderBuilder viewOrder() {
        ensureDefaults();
        return new ViewOrderBuilder(app, scenario);
    }
}
