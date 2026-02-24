package com.optivem.eshop.systemtest.e2etests.v3.base;

import com.optivem.eshop.systemtest.base.v3.BaseDriverTest;
import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

public abstract class BaseE2eTest extends BaseDriverTest {

    @BeforeEach
    void setUpDrivers() {
        setShopDriver();
        setUpExternalDrivers();
    }

    protected abstract void setShopDriver();

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }

    protected String createUniqueSku(String baseSku) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return baseSku + "-" + suffix;
    }
}
