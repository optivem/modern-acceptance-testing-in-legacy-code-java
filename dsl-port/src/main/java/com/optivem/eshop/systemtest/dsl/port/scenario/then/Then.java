package com.optivem.eshop.systemtest.dsl.port.scenario.then;

import com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.ThenClock;
import com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.ThenCountry;
import com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.ThenProduct;

public interface Then {
    ThenClock clock();

    ThenProduct product(String skuAlias);

    ThenCountry country(String countryAlias);
}
