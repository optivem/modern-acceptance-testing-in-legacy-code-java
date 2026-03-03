package com.optivem.eshop.systemtest.dsl.port.erp.given.steps;

import com.optivem.eshop.systemtest.dsl.port.erp.given.steps.base.GivenStep;

public interface GivenReturnsProduct extends GivenStep {
    GivenReturnsProduct sku(String skuParamAlias);

    GivenReturnsProduct unitPrice(String unitPrice);

    GivenReturnsProduct unitPrice(double unitPrice);
}
