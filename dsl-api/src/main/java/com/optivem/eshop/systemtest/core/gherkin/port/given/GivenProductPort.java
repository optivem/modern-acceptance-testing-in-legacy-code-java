package com.optivem.eshop.systemtest.dsl.api;

public interface GivenProductPort extends GivenStepPort {
    GivenProductPort withSku(String sku);

    GivenProductPort withUnitPrice(String unitPrice);

    GivenProductPort withUnitPrice(double unitPrice);

}
