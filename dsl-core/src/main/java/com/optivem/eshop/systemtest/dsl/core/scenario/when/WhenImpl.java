package com.optivem.eshop.systemtest.dsl.core.scenario.when;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenBrowseCouponsImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenGoToErpImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenGoToTaxImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenGoToClockImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenCancelOrderImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenGoToShopImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenPlaceOrderImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenPublishCouponImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.steps.WhenViewOrderImpl;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.When;

import static com.optivem.eshop.systemtest.dsl.core.scenario.ScenarioDefaults.*;

public class WhenImpl implements When {
    private final AppDsl app;
    private boolean hasProduct;
    private boolean hasTaxRate;

    public WhenImpl(AppDsl app, boolean hasProduct, boolean hasTaxRate) {
        this.app = app;
        this.hasProduct = hasProduct;
        this.hasTaxRate = hasTaxRate;
    }

    public WhenImpl(AppDsl app) {
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

    public WhenGoToErpImpl goToErp() {
        return new WhenGoToErpImpl(app);
    }

    public WhenGoToTaxImpl goToTax() {
        return new WhenGoToTaxImpl(app);
    }

    public WhenGoToClockImpl goToClock() {
        return new WhenGoToClockImpl(app);
    }
}

