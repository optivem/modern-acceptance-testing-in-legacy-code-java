package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.dsl.DslFactory;
import com.optivem.eshop.systemtest.core.dsl.tax.TaxDsl;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxSmokeTest {

    private TaxDsl tax;

    @BeforeEach
    void setUp() {
        DslFactory dslFactory = new DslFactory();
        tax = dslFactory.createTaxDsl();
    }

    @AfterEach
    void tearDown() {
        Closer.close(tax);
    }

    @Test
    void shouldBeAbleToGoToTax() {
        tax.goToTax()
                .execute()
                .shouldSucceed();
    }
}

