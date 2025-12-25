package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

public class OrderBuilder {
    private final GivenClause givenClause;
    private String orderNumber;
    private String sku;
    private int quantity = 1; // Default to 1 if not specified

    public OrderBuilder(GivenClause givenClause) {
        this.givenClause = givenClause;
    }

    public OrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public OrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public OrderBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public GivenClause and() {
        return givenClause;
    }

    public WhenClause when() {
        return givenClause.when();
    }

    String getOrderNumber() {
        return orderNumber;
    }

    String getSku() {
        return sku;
    }

    int getQuantity() {
        return quantity;
    }
}
