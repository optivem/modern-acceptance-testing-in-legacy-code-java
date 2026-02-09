package com.optivem.eshop.systemtest.e2etests.v1.base;

import com.optivem.eshop.systemtest.base.v1.BaseRawTest;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseE2eTest extends BaseRawTest {

    @BeforeEach
    void setUp() {
        setShopDriver();
        setUpExternalHttpClients();
    }

    protected abstract void setShopDriver();
}
