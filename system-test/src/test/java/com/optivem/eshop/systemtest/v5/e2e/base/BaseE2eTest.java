package com.optivem.eshop.systemtest.v5.e2e.base;

import com.optivem.eshop.systemtest.v5.base.BaseAppDslTest;
import com.optivem.eshop.dsl.port.ExternalSystemMode;

public abstract class BaseE2eTest extends BaseAppDslTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}




