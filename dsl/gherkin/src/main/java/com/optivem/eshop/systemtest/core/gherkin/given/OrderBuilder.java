package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.enums.OrderStatus;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class OrderBuilder {
    private final GivenClause givenClause;

    private String orderNumber;
    private String sku;
    private String quantity;
    private String country;
    private OrderStatus status;

    public OrderBuilder(GivenClause givenClause) {
        this.givenClause = givenClause;

        withOrderNumber(DEFAULT_ORDER_NUMBER);
        withSku(DEFAULT_SKU);
        withQuantity(DEFAULT_QUANTITY);
        withCountry(DEFAULT_COUNTRY);
        withStatus(DEFAULT_ORDER_STATUS);
    }

    public OrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public OrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public OrderBuilder withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder withQuantity(int quantity) {
        return withQuantity(String.valueOf(quantity));
    }

    public OrderBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public OrderBuilder withStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public GivenClause and() {
        return givenClause;
    }

    public WhenClause when() {
        return givenClause.when();
    }

    void execute(SystemDsl app) {

        app.shop().placeOrder()
                .orderNumber(orderNumber)
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .execute()
                .shouldSucceed();

        if(status == OrderStatus.CANCELLED) {
            app.shop().cancelOrder()
                    .orderNumber(orderNumber)
                    .execute()
                    .shouldSucceed();
        }
    }
}
