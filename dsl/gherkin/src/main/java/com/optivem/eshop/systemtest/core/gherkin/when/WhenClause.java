package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class WhenClause {
    private final SystemDsl app;
    private boolean hasProduct;
    private boolean hasTaxRate;

    public WhenClause(SystemDsl app, boolean hasProduct, boolean hasTaxRate) {
        this.app = app;
        this.hasProduct = hasProduct;
        this.hasTaxRate = hasTaxRate;
    }

    public WhenClause(SystemDsl app) {
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
    
    public GoToShopBuilder goToShop() {
        return new GoToShopBuilder(app);
    }

    public PlaceOrderBuilder placeOrder() {
        ensureDefaults();
        return new PlaceOrderBuilder(app);
    }

    public CancelOrderBuilder cancelOrder() {
        ensureDefaults();
        return new CancelOrderBuilder(app);
    }

    public ViewOrderBuilder viewOrder() {
        ensureDefaults();
        return new ViewOrderBuilder(app);
    }

    public PublishCouponBuilder publishCoupon() {
        return new PublishCouponBuilder(app);
    }

    public BrowseCouponsBuilder browseCoupons() {
        return new BrowseCouponsBuilder(app);
    }
}
