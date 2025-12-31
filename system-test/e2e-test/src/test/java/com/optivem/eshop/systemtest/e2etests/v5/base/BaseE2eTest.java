package com.optivem.eshop.systemtest.e2etests.v5.base;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import com.optivem.eshop.systemtest.configuration.Environment;
import com.optivem.testing.dsl.ExternalSystemMode;

public abstract class BaseE2eTest extends BaseSystemDslTest {

    @Override
    protected Environment getFixedEnvironment() {
        return Environment.LOCAL;
    }

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}
