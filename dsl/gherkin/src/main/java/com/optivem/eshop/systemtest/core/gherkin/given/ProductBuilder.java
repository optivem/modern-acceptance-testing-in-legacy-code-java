package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class ProductBuilder {
    private final GivenClause givenClause;
    private String sku;
    private double unitPrice;

    public ProductBuilder(GivenClause givenClause) {
        this.givenClause = givenClause;
        withSku(DEFAULT_SKU);
        withUnitPrice(DEFAULT_UNIT_PRICE);
    }

    public ProductBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public ProductBuilder withUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public ProductBuilder withUnitPrice(String unitPrice) {
        this.unitPrice = Double.parseDouble(unitPrice);
        return this;
    }

    public GivenClause and() {
        return givenClause;
    }

    public WhenClause when() {
        return givenClause.when();
    }

    void execute(SystemDsl app) {
        app.erp().returnsProduct()
                .sku(sku)
                .unitPrice(unitPrice)
                .execute()
                .shouldSucceed();
    }
}
