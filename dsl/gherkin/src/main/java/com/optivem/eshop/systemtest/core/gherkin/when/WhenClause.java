package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class WhenClause {
    private static final Logger log = LoggerFactory.getLogger(WhenClause.class);
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private boolean hasProduct;
    private boolean hasTaxRate;

    public WhenClause(SystemDsl app, ScenarioDsl scenario) {
        this(app, scenario, false, false);
    }

    public WhenClause(SystemDsl app, ScenarioDsl scenario, boolean hasProduct, boolean hasTaxRate) {
        this.app = app;
        this.scenario = scenario;
        this.hasProduct = hasProduct;
        this.hasTaxRate = hasTaxRate;
    }

    private void ensureDefaults() {
        long start = System.currentTimeMillis();
        if (!hasProduct) {
            long pStart = System.currentTimeMillis();
            app.erp().returnsProduct()
                    .sku(DEFAULT_SKU)
                    .unitPrice(DEFAULT_UNIT_PRICE)
                    .execute()
                    .shouldSucceed();
            hasProduct = true;
            log.info("[PERF] ensureDefaults - default product took {}ms", System.currentTimeMillis() - pStart);
        }

        if (!hasTaxRate) {
            long tStart = System.currentTimeMillis();
            app.tax().returnsTaxRate()
                    .country(DEFAULT_COUNTRY)
                    .taxRate(DEFAULT_TAX_RATE)
                    .execute()
                    .shouldSucceed();
            hasTaxRate = true;
            log.info("[PERF] ensureDefaults - default taxRate took {}ms", System.currentTimeMillis() - tStart);
        }
        long elapsed = System.currentTimeMillis() - start;
        log.info("[PERF] WhenClause.ensureDefaults total took {}ms", elapsed);
    }

    public PlaceOrderBuilder placeOrder() {
        ensureDefaults();
        return new PlaceOrderBuilder(app, scenario);
    }

    public CancelOrderBuilder cancelOrder() {
        ensureDefaults();
        return new CancelOrderBuilder(app, scenario);
    }

    public ViewOrderBuilder viewOrder() {
        ensureDefaults();
        return new ViewOrderBuilder(app, scenario);
    }

    public PublishCouponBuilder publishCoupon() {
        return new PublishCouponBuilder(app, scenario);
    }

    public BrowseCouponsBuilder browseCoupons() {
        return new BrowseCouponsBuilder(app, scenario);
    }
}
