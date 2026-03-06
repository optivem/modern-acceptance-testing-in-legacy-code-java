package com.optivem.eshop.systemtest.dsl.port.then.steps.base;

import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenClock;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenCountry;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenCoupon;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenOrder;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenProduct;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenReview;

public interface ThenStep<TThen> {
    TThen and();

    ThenOrder order();

    ThenOrder order(String orderNumber);

    ThenCoupon coupon();

    ThenCoupon coupon(String couponCode);

    ThenClock clock();

    ThenProduct product(String skuAlias);

    ThenCountry country(String countryAlias);

    ThenReview review();
}

