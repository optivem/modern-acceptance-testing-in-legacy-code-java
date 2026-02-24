package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.port.GivenOrderPort;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenOrder extends BaseGivenStep implements GivenOrderPort {
    private String orderNumber;
    private String sku;
    private String quantity;
    private String country;
    private String couponCodeAlias;
    private OrderStatus status;

    public GivenOrder(Given given) {
        super(given);

        withOrderNumber(DEFAULT_ORDER_NUMBER);
        withSku(DEFAULT_SKU);
        withQuantity(DEFAULT_QUANTITY);
        withCountry(DEFAULT_COUNTRY);
        withCouponCode(EMPTY);
        withStatus(DEFAULT_ORDER_STATUS);
    }

    public GivenOrder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public GivenOrder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public GivenOrder withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public GivenOrder withQuantity(int quantity) {
        return withQuantity(Converter.fromInteger(quantity));
    }

    public GivenOrder withCountry(String country) {
        this.country = country;
        return this;
    }

    public GivenOrder withCouponCode(String couponCodeAlias) {
        this.couponCodeAlias = couponCodeAlias;
        return this;
    }

    public GivenOrder withStatus(OrderStatus status) {
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