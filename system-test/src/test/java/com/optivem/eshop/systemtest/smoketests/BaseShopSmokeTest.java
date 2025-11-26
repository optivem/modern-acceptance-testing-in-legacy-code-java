package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.clients.commons.Closer;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseShopSmokeTest {

    private ShopDriver shopDriver;

    @BeforeEach
    void setUp() {
        shopDriver = createDriver();
    }

    protected abstract ShopDriver createDriver();

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
    }

    @Test
    void shouldBeAbleToGoToShop() {
        var result = shopDriver.goToShop();
        assertThat(result.isSuccess()).isTrue();
    }
}
