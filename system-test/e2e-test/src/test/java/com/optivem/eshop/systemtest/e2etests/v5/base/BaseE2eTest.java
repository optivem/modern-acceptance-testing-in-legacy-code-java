package com.optivem.eshop.systemtest.e2etests.v5.base;

import com.optivem.eshop.systemtest.base.v5.BaseAppDslTest;
import com.optivem.eshop.systemtest.dsl.common.ExternalSystemMode;

public abstract class BaseE2eTest extends BaseAppDslTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}



