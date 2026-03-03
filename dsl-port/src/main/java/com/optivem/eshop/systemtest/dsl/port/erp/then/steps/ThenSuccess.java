package com.optivem.eshop.systemtest.dsl.port.erp.then.steps;

import com.optivem.eshop.systemtest.dsl.port.erp.then.steps.base.ThenStep;

import java.math.BigDecimal;

public interface ThenSuccess extends ThenStep<ThenSuccess> {
    ThenSuccess sku(String skuParamAlias);

    ThenSuccess price(BigDecimal expectedPrice);

    ThenSuccess price(double expectedPrice);

    ThenSuccess price(String expectedPrice);
}
