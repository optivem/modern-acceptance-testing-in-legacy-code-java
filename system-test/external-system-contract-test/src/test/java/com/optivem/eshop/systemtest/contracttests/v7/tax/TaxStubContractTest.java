package com.optivem.eshop.systemtest.contracttests.v7.tax;

import com.optivem.eshop.systemtest.dsl.core.app.shared.ExternalSystemMode;
import org.junit.jupiter.api.Test;

class TaxStubContractTest extends BaseTaxContractTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }

    @Test
    void shouldBeAbleToGetConfiguredTaxRate() {
        scenario
                .given().country().withCode("LALA").withTaxRate(0.23)
                .then().country("LALA").hasCountry("LALA").hasTaxRate(0.23);
    }
}
