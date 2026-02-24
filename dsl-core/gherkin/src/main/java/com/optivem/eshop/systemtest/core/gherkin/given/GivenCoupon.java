package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.dsl.api.given.GivenCouponPort;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenCoupon extends BaseGivenStep implements GivenCouponPort {
    private String couponCode;
    private String discountRate;
    private String validFrom;
    private String validTo;
    private String usageLimit;

    public GivenCoupon(Given given) {
        super(given);

        withCouponCode(DEFAULT_COUPON_CODE);
        withDiscountRate(DEFAULT_DISCOUNT_RATE);
        withValidFrom(EMPTY);
        withValidTo(EMPTY);
        withUsageLimit(EMPTY);
    }

    public GivenCoupon withCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public GivenCoupon withDiscountRate(String discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public GivenCoupon withDiscountRate(double discountRate) {
        return withDiscountRate(Converter.fromDouble(discountRate));
    }

    public GivenCoupon withValidFrom(String validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public GivenCoupon withValidTo(String validTo) {
        this.validTo = validTo;
        return this;
    }

    public GivenCoupon withUsageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public GivenCoupon withUsageLimit(int usageLimit) {
        return withUsageLimit(Converter.fromInteger(usageLimit));
    }

    @Override
    void execute(SystemDsl app) {
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