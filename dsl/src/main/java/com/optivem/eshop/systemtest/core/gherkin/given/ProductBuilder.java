package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

public class ProductBuilder {
    private final GivenClause givenClause;
    private String sku;
    private double unitPrice;

    public ProductBuilder(GivenClause givenClause) {
        this.givenClause = givenClause;
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

    String getSku() {
        return sku;
    }

    double getUnitPrice() {
        return unitPrice;
    }
}
