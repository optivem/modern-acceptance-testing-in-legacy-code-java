package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class PublishCouponBuilder {
    private final SystemDsl app;
    private final ScenarioDsl scenario;

    private String couponCode;
    private String discountRate;

    public PublishCouponBuilder(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;

        withCouponCode(DEFAULT_COUPON_CODE);
        withDiscountRate(DEFAULT_DISCOUNT_RATE);
    }

    public PublishCouponBuilder withCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public PublishCouponBuilder withDiscountRate(String discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public PublishCouponBuilder withDiscountRate(double discountRate) {
        return withDiscountRate(String.valueOf(discountRate));
    }

    public ThenClause then() {
        var command = app.shop().publishCoupon();

        if (couponCode != null) {
            command.couponCode(couponCode);
        }

        if (discountRate != null) {
            command.discountRate(discountRate);
        }

        var result = command.execute();
        return new ThenClause(app, scenario, null, result);
    }
}

