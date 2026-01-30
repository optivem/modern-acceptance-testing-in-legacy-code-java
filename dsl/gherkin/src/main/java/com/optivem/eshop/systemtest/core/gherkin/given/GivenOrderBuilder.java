package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.commons.util.Converter;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenOrderBuilder extends BaseGivenBuilder {
    private String orderNumber;
    private String sku;
    private String quantity;
    private String country;
    private String couponCodeAlias;
    private OrderStatus status;

    public GivenOrderBuilder(GivenClause givenClause) {
        super(givenClause);

        withOrderNumber(DEFAULT_ORDER_NUMBER);
        withSku(DEFAULT_SKU);
        withQuantity(DEFAULT_QUANTITY);
        withCountry(DEFAULT_COUNTRY);
        withCouponCode(EMPTY);
        withStatus(DEFAULT_ORDER_STATUS);
    }

    public GivenOrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public GivenOrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public GivenOrderBuilder withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public GivenOrderBuilder withQuantity(int quantity) {
        return withQuantity(Converter.fromInteger(quantity));
    }

    public GivenOrderBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public GivenOrderBuilder withCouponCode(String couponCodeAlias) {
        this.couponCodeAlias = couponCodeAlias;
        return this;
    }

    public GivenOrderBuilder withStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    @Override
    void execute(SystemDsl app) {

        app.shop().placeOrder()
                .orderNumber(orderNumber)
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .couponCode(couponCodeAlias)
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
