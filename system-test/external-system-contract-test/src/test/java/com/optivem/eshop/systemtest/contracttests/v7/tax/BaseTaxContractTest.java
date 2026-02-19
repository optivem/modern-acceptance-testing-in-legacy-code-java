package com.optivem.eshop.systemtest.contracttests.v7.tax;

import com.optivem.eshop.systemtest.contracttests.v7.base.BaseExternalSystemContractTest;
import org.junit.jupiter.api.Test;

public abstract class BaseTaxContractTest extends BaseExternalSystemContractTest {

    @Test
    void shouldBeAbleToGetTaxRate() {
        app.tax().returnsTaxRate()
                .country("US")
                .taxRate(0.09)
                .execute()
                .shouldSucceed();

        app.tax().getTaxRate()
                .country("US")
                .execute()
                .shouldSucceed()
                .country("US")
                .taxRateIsPositive();
    }
}
