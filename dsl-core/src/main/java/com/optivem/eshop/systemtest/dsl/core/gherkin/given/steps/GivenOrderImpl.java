package com.optivem.eshop.systemtest.dsl.core.gherkin.given.steps;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenOrderPort;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.orders.OrderStatus;

import static com.optivem.eshop.systemtest.dsl.core.gherkin.GherkinDefaults.*;

public class GivenOrderImpl extends BaseGivenStep implements GivenOrderPort {
    private String orderNumber;
    private String sku;
    private String quantity;
    private String country;
    private String couponCodeAlias;
    private OrderStatus status;

    public GivenOrderImpl(GivenImpl given) {
        super(given);

        withOrderNumber(DEFAULT_ORDER_NUMBER);
        withSku(DEFAULT_SKU);
        withQuantity(DEFAULT_QUANTITY);
        withCountry(DEFAULT_COUNTRY);
        withCouponCode(EMPTY);
        withStatus(DEFAULT_ORDER_STATUS);
    }

    public GivenOrderImpl withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public GivenOrderImpl withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public GivenOrderImpl withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public GivenOrderImpl withQuantity(int quantity) {
        return withQuantity(Converter.fromInteger(quantity));
    }

    public GivenOrderImpl withCountry(String country) {
        this.country = country;
        return this;
    }

    public GivenOrderImpl withCouponCode(String couponCodeAlias) {
        this.couponCodeAlias = couponCodeAlias;
        return this;
    }

    public GivenOrderImpl withStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public void execute(SystemDsl app) {

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