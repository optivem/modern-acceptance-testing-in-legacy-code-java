package com.optivem.eshop.systemtest.core.gherkin.given.steps;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.core.system.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenCouponPort;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenCouponImpl extends BaseGivenStep implements GivenCouponPort {
    private String couponCode;
    private String discountRate;
    private String validFrom;
    private String validTo;
    private String usageLimit;

    public GivenCouponImpl(GivenImpl given) {
        super(given);

        withCouponCode(DEFAULT_COUPON_CODE);
        withDiscountRate(DEFAULT_DISCOUNT_RATE);
        withValidFrom(EMPTY);
        withValidTo(EMPTY);
        withUsageLimit(EMPTY);
    }

    public GivenCouponImpl withCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public GivenCouponImpl withDiscountRate(String discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public GivenCouponImpl withDiscountRate(double discountRate) {
        return withDiscountRate(Converter.fromDouble(discountRate));
    }

    public GivenCouponImpl withValidFrom(String validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public GivenCouponImpl withValidTo(String validTo) {
        this.validTo = validTo;
        return this;
    }

    public GivenCouponImpl withUsageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public GivenCouponImpl withUsageLimit(int usageLimit) {
        return withUsageLimit(Converter.fromInteger(usageLimit));
    }

    @Override
    public void execute(SystemDsl app) {
        var result = app.shop().publishCoupon()
                .couponCode(couponCode)
                .discountRate(discountRate)
                .validFrom(validFrom)
                .validTo(validTo)
                .usageLimit(usageLimit)
                .execute();

        result.shouldSucceed();
    }
}