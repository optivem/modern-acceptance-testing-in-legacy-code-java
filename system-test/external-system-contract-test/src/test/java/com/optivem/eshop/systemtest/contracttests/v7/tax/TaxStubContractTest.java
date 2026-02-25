package com.optivem.eshop.systemtest.contracttests.v7.tax;

import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;
import org.junit.jupiter.api.Test;

class TaxStubContractTest extends BaseTaxContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }

    @Test
    void shouldBeAbleToGetConfiguredTaxRate() {
        app.tax().returnsTaxRate()
                .country("LALA")
                .taxRate(0.23)
                .execute()
                .shouldSucceed();

        app.tax().getTaxRate()
                .country("LALA")
                .execute()
                .shouldSucceed()
                .country("LALA")
                .taxRate(0.23);
    }
}

