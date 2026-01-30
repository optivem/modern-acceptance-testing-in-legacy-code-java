package com.optivem.eshop.systemtest.core.gherkin.when;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.PlaceOrderVerification;

public class PlaceOrderBuilder extends BaseWhenBuilder<PlaceOrderResponse, PlaceOrderVerification> {
    private String orderNumber;
    private String sku;
    private String quantity;
    private String country;
    private String couponCode;

    public PlaceOrderBuilder(SystemDsl app, ScenarioDsl scenario) {
        super(app, scenario);
        withOrderNumber(DEFAULT_ORDER_NUMBER);
        withSku(DEFAULT_SKU);
        withQuantity(DEFAULT_QUANTITY);
        withCountry(DEFAULT_COUNTRY);
        withCouponCode(EMPTY);
    }

    public PlaceOrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public PlaceOrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public PlaceOrderBuilder withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public PlaceOrderBuilder withQuantity(int quantity) {
        return withQuantity(Converter.fromInteger(quantity));
    }

    public PlaceOrderBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public PlaceOrderBuilder withCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public PlaceOrderBuilder withCouponCode() {
        return withCouponCode(DEFAULT_COUPON_CODE);
    }

    @Override
    protected ExecutionResult<PlaceOrderResponse, PlaceOrderVerification> execute(SystemDsl app) {
        var result = app.shop().placeOrder()
                .orderNumber(orderNumber)
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .couponCode(couponCode)
                .execute();

        return new ExecutionResultBuilder<>(result)
                .orderNumber(orderNumber)
                .couponCode(couponCode)
                .build();
    }
}