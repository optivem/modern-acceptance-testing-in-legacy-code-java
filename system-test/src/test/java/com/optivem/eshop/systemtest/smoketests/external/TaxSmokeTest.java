package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.DslFactory;
import com.optivem.eshop.systemtest.dsl.Dsl;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxSmokeTest {

    private Dsl dsl;

    @BeforeEach
    void setUp() {
        dsl = DslFactory.create();
    }

    @AfterEach
    void tearDown() {
        Closer.close(dsl);
    }

    @Test
    void shouldBeAbleToGoToTax() {
        dsl.tax().goToTax()
                .execute()
                .shouldSucceed();
    }
}

