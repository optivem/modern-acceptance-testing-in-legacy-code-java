package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.ErpDsl;
import com.optivem.lang.Closer;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ErpSmokeTest {

    private ErpApiDriver erpApiDriver;
    private ErpDsl erp;

    @BeforeEach
    void setUp() {
        this.erpApiDriver = DriverFactory.createErpApiDriver();

        var context = new DslContext();
        erp = new ErpDsl(erpApiDriver, context);
    }

    @AfterEach
    void tearDown() {
        Closer.close(erpApiDriver);
    }

    @Test
    void shouldBeAbleToGoToErp() {
        erp.goToErp()
                .execute()
                .shouldSucceed();
    }
}

