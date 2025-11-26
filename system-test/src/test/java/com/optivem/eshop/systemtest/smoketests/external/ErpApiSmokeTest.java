package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.clients.commons.Closer;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.ErpApiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ErpApiSmokeTest {

    private ErpApiDriver erpApiDriver;

    @BeforeEach
    void setUp() {
        this.erpApiDriver = DriverFactory.createErpApiDriver();
    }

    @AfterEach
    void tearDown() {
        Closer.close(erpApiDriver);
    }

    @Test
    void shouldBeAbleToGoToErp() {
        var result = erpApiDriver.goToErp();
        assertThat(result.isSuccess()).isTrue();
    }
}

