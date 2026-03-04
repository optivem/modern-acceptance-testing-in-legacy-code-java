package com.optivem.eshop.systemtest.contracttests.v7.tax;

import com.optivem.eshop.systemtest.contracttests.v7.base.BaseExternalSystemContractTest;
import org.junit.jupiter.api.Test;

public abstract class BaseTaxContractTest extends BaseExternalSystemContractTest {
    @Test
    void shouldBeAbleToGetTaxRate() {
        scenario
                .given().country().withCode("US").withTaxRate(0.09)
                .then().country("US").hasTaxRateIsPositive();
    }
}
