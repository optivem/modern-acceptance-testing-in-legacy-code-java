package com.optivem.eshop.systemtest.e2etests.v2.base;

import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;
import com.optivem.eshop.systemtest.base.v2.BaseClientTest;

import org.junit.jupiter.api.BeforeEach;

public abstract class BaseE2eTest extends BaseClientTest {
    @BeforeEach
    void setUp() {
        setShopDriver();
        setUpExternalClients();
    }

    protected abstract void setShopDriver();

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}

