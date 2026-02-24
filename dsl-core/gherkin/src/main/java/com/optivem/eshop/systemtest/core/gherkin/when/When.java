package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.dsl.api.WhenPort;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class When implements WhenPort {
    private final SystemDsl app;
    private boolean hasProduct;
    private boolean hasTaxRate;

    public When(SystemDsl app, boolean hasProduct, boolean hasTaxRate) {
        this.app = app;
        this.hasProduct = hasProduct;
        this.hasTaxRate = hasTaxRate;
    }

    public When(SystemDsl app) {
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

    public WhenGoToShop goToShop() {
        return new WhenGoToShop(app);
    }

    public WhenPlaceOrder placeOrder() {
        ensureDefaults();
        return new WhenPlaceOrder(app);
    }

    public WhenCancelOrder cancelOrder() {
        ensureDefaults();
        return new WhenCancelOrder(app);
    }

    public WhenViewOrder viewOrder() {
        ensureDefaults();
        return new WhenViewOrder(app);
    }

    public WhenPublishCoupon publishCoupon() {
        return new WhenPublishCoupon(app);
    }

    public WhenBrowseCoupons browseCoupons() {
        return new WhenBrowseCoupons(app);
    }
}