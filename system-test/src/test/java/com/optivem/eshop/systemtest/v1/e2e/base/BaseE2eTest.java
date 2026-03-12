package com.optivem.eshop.systemtest.v1.e2e.base;

import com.optivem.eshop.dsl.port.ExternalSystemMode;
import com.optivem.eshop.systemtest.v1.base.BaseRawTest;

import org.junit.jupiter.api.BeforeEach;

public abstract class BaseE2eTest extends BaseRawTest {
    @BeforeEach
    void setUp() {
        setShopDriver();
        setUpExternalHttpClients();
    }

    protected abstract void setShopDriver();

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}



