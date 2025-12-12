package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.SystemDslFactory;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxSmokeTest {

    private SystemDsl app;

    @BeforeEach
    void setUp() {
        app = SystemDslFactory.create();
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }

    @Test
    void shouldBeAbleToGoToTax() {
        app.tax().goToTax()
                .execute()
                .shouldSucceed();
    }
}

