package com.optivem.eshop.systemtest.dsl.api.given.steps;

import com.optivem.eshop.systemtest.dsl.api.given.steps.base.GivenStep;

public interface GivenProduct extends GivenStep {
    GivenProduct withSku(String sku);

    GivenProduct withUnitPrice(String unitPrice);

    GivenProduct withUnitPrice(double unitPrice);

}

