package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.dsl.Dsl;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ErpSmokeTest {

    private Dsl dsl;

    @BeforeEach
    void setUp() {
        dsl = new Dsl();
    }

    @AfterEach
    void tearDown() {
        Closer.close(dsl);
    }

    @Test
    void shouldBeAbleToGoToErp() {
        dsl.erp().goToErp()
                .execute()
                .shouldSucceed();
    }
}

