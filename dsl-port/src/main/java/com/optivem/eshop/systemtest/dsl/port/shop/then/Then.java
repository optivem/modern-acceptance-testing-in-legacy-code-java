package com.optivem.eshop.systemtest.dsl.port.shop.then;

import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenClock;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenCountry;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenProduct;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenSuccess;

public interface Then {
    ThenClock clock();

    ThenProduct product(String skuAlias);

    ThenCountry country(String countryAlias);

    ThenSuccess shouldSucceed();

    ThenFailure shouldFail();
}

