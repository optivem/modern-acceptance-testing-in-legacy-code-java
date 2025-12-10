package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.dsl.DslFactory;
import com.optivem.eshop.systemtest.core.dsl.erp.ErpDsl;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ErpSmokeTest {

    private ErpDsl erp;

    @BeforeEach
    void setUp() {
        DslFactory dslFactory = new DslFactory();
        erp = dslFactory.createErpDsl();
    }

    @AfterEach
    void tearDown() {
        Closer.close(erp);
    }

    @Test
    void shouldBeAbleToGoToErp() {
        erp.goToErp()
                .execute()
                .shouldSucceed();
    }
}

