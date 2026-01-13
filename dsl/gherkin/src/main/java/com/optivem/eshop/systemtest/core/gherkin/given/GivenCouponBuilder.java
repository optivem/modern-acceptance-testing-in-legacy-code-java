package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;
import com.optivem.lang.Converter;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class GivenCouponBuilder extends BaseGivenBuilder {
    private String couponCode;
    private String discountRate;
    private String validFrom;
    private String validTo;
    private String usageLimit;

    public GivenCouponBuilder(GivenClause givenClause) {
        super(givenClause);

        withCouponCode(DEFAULT_COUPON_CODE);
        withDiscountRate(DEFAULT_DISCOUNT_RATE);
        withValidFrom(EMPTY);
        withValidTo(EMPTY);
        withUsageLimit(EMPTY);
    }

    public GivenCouponBuilder withCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public GivenCouponBuilder withDiscountRate(double discountRate) {
        this.discountRate = Converter.fromDouble(discountRate);
        return this;
    }

    public GivenCouponBuilder withDiscountRate(String discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public GivenCouponBuilder withValidFrom(String validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public GivenCouponBuilder withValidTo(String validTo) {
        this.validTo = validTo;
        return this;
    }

    public GivenCouponBuilder withUsageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public GivenCouponBuilder withUsageLimit(int usageLimit) {
        withUsageLimit(String.valueOf(usageLimit));
        return this;
    }

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

