package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.steps.WhenBrowseCouponsImpl;
import com.optivem.eshop.systemtest.core.gherkin.when.steps.WhenCancelOrderImpl;
import com.optivem.eshop.systemtest.core.gherkin.when.steps.WhenGoToShopImpl;
import com.optivem.eshop.systemtest.core.gherkin.when.steps.WhenPlaceOrderImpl;
import com.optivem.eshop.systemtest.core.gherkin.when.steps.WhenPublishCouponImpl;
import com.optivem.eshop.systemtest.core.gherkin.when.steps.WhenViewOrderImpl;
import com.optivem.eshop.systemtest.dsl.api.when.WhenPort;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class WhenImpl implements WhenPort {
    private final SystemDsl app;
    private boolean hasProduct;
    private boolean hasTaxRate;

    public WhenImpl(SystemDsl app, boolean hasProduct, boolean hasTaxRate) {
        this.app = app;
        this.hasProduct = hasProduct;
        this.hasTaxRate = hasTaxRate;
    }

    public WhenImpl(SystemDsl app) {
        this(app, false, false);
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

    public WhenGoToShopImpl goToShop() {
        return new WhenGoToShopImpl(app);
    }

    public WhenPlaceOrderImpl placeOrder() {
        ensureDefaults();
        return new WhenPlaceOrderImpl(app);
    }

    public WhenCancelOrderImpl cancelOrder() {
        ensureDefaults();
        return new WhenCancelOrderImpl(app);
    }

    public WhenViewOrderImpl viewOrder() {
        ensureDefaults();
        return new WhenViewOrderImpl(app);
    }

    public WhenPublishCouponImpl publishCoupon() {
        return new WhenPublishCouponImpl(app);
    }

    public WhenBrowseCouponsImpl browseCoupons() {
        return new WhenBrowseCouponsImpl(app);
    }
}