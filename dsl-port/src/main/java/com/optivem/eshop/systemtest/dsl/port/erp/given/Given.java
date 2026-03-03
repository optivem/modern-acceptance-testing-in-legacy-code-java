package com.optivem.eshop.systemtest.dsl.port.erp.given;

import com.optivem.eshop.systemtest.dsl.port.erp.given.steps.GivenReturnsProduct;
import com.optivem.eshop.systemtest.dsl.port.erp.when.When;

public interface Given {
    GivenReturnsProduct returnsProduct();

    When when();
}
