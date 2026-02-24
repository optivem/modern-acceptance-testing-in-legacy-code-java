package com.optivem.eshop.systemtest.core.gherkin.port;

public interface GivenProductPort {
    GivenProductPort withSku(String sku);

    GivenProductPort withUnitPrice(String unitPrice);

    GivenProductPort withUnitPrice(double unitPrice);

    GivenPort and();

    WhenPort when();
}
