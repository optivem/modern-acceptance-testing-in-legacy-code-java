package com.optivem.eshop.systemtest.e2etests.v6.base;

import com.optivem.eshop.systemtest.base.v6.BaseScenarioDslTest;
import com.optivem.eshop.systemtest.configuration.Environment;
import com.optivem.test.dsl.ExternalSystemMode;

public class BaseE2eTest extends BaseScenarioDslTest {

    @Override
    protected Environment getFixedEnvironment() {
        return Environment.LOCAL;
    }

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}

