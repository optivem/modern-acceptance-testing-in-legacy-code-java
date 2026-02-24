package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;
import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenPublishCouponPort;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class WhenPublishCoupon extends BaseWhenStep<Void, VoidVerification> implements WhenPublishCouponPort {
    private String couponCode;
    private String discountRate;
    private String validFrom;
    private String validTo;
    private String usageLimit;

    public WhenPublishCoupon(SystemDsl app) {
        super(app);
        withCouponCode(DEFAULT_COUPON_CODE);
        withDiscountRate(DEFAULT_DISCOUNT_RATE);
    }

    public WhenPublishCoupon withCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public WhenPublishCoupon withDiscountRate(String discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public WhenPublishCoupon withDiscountRate(double discountRate) {
        return withDiscountRate(Converter.fromDouble(discountRate));
    }

    public WhenPublishCoupon withValidFrom(String validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public WhenPublishCoupon withValidTo(String validTo) {
        this.validTo = validTo;
        return this;
    }

    public WhenPublishCoupon withUsageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public WhenPublishCoupon withUsageLimit(int usageLimit) {
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
