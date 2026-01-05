package com.optivem.eshop.systemtest.core.gherkin.when;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.GivenOrderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaceOrderBuilder extends BaseWhenBuilder {
    private static final Logger log = LoggerFactory.getLogger(PlaceOrderBuilder.class);
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
        return withQuantity(String.valueOf(quantity));
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
    protected ExecutionResult execute(SystemDsl app) {
        long start = System.currentTimeMillis();
        var result = app.shop().placeOrder()
                .orderNumber(orderNumber)
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .couponCode(couponCode)
                .execute();
        long elapsed = System.currentTimeMillis() - start;
        log.info("[PERF] PlaceOrderBuilder.execute took {}ms", elapsed);

        return ExecutionResult.builder(result)
                .orderNumber(orderNumber)
                .couponCode(couponCode)
                .build();
    }
}
