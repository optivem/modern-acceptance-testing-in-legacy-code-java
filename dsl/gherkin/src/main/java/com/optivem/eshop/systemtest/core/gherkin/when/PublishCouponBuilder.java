package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.commons.util.Converter;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class PublishCouponBuilder extends BaseWhenBuilder<Void, VoidVerification> {
    private String couponCode;
    private String discountRate;
    private String validFrom;
    private String validTo;
    private String usageLimit;

    public PublishCouponBuilder(SystemDsl app, ScenarioDsl scenario) {
        super(app, scenario);
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
        return withDiscountRate(Converter.fromDouble(discountRate));
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

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(SystemDsl app) {
        var result = app.shop().publishCoupon()
                .couponCode(couponCode)
                .discountRate(discountRate)
                .validFrom(validFrom)
                .validTo(validTo)
                .usageLimit(usageLimit)
                .execute();

        return new ExecutionResultBuilder<>(result)
                .couponCode(couponCode)
                .build();
    }
}

