package com.optivem.eshop.systemtest.dsl.port.shop.then;

import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenClock;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenErp;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenSuccess;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenTax;

public interface Then {
    ThenClock clock();

    ThenErp product(String skuAlias);

    ThenTax country(String countryAlias);

    ThenSuccess shouldSucceed();

    ThenFailure shouldFail();
}

