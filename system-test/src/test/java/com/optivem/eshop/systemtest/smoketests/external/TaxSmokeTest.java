package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.tax.TaxDsl;
import com.optivem.lang.Closer;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxSmokeTest {

    private TaxApiDriver taxApiDriver;
    private TaxDsl tax;

    @BeforeEach
    void setUp() {
        this.taxApiDriver = DriverFactory.createTaxApiDriver();

        var context = new DslContext();
        tax = new TaxDsl(taxApiDriver, context);
    }

    @AfterEach
    void tearDown() {
        Closer.close(taxApiDriver);
    }

    @Test
    void shouldBeAbleToGoToTax() {
        tax.goToTax()
                .execute()
                .expectSuccess();
    }
}

