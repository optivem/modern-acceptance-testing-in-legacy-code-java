package com.optivem.eshop.systemtest.core.gherkin.port;

public interface GivenProductPort extends GivenStep {
    GivenProductPort withSku(String sku);

    GivenProductPort withUnitPrice(String unitPrice);

    GivenProductPort withUnitPrice(double unitPrice);

}
