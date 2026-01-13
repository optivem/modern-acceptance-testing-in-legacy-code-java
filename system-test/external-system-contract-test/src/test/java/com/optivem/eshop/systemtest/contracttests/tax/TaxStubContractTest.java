package com.optivem.eshop.systemtest.contracttests.tax;

import com.optivem.eshop.systemtest.configuration.Environment;
import com.optivem.test.dsl.ExternalSystemMode;
import org.junit.jupiter.api.Test;

public class TaxStubContractTest extends BaseTaxContractTest {

    @Override
    protected Environment getFixedEnvironment() {
        return Environment.LOCAL;
    }

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
