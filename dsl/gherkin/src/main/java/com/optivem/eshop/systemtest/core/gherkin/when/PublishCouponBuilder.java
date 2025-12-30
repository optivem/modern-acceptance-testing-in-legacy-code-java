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
    private String validFrom;
    private String validTo;
    private String usageLimit;

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

    public PublishCouponBuilder withValidFrom(String validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public PublishCouponBuilder withValidTo(String validTo) {
        this.validTo = validTo;
        return this;
    }

    public PublishCouponBuilder withUsageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public PublishCouponBuilder withUsageLimit(int usageLimit) {
        return withUsageLimit(String.valueOf(usageLimit));
    }

    public ThenClause then() {
        var result = app.shop().publishCoupon()
                .couponCode(couponCode)
                .discountRate(discountRate)
                .validFrom(validFrom)
                .validTo(validTo)
                .usageLimit(usageLimit)
                .execute();
        return new ThenClause(app, scenario, null, result);
    }
}

