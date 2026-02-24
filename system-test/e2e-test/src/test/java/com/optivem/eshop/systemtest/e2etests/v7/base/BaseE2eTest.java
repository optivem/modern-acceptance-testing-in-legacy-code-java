package com.optivem.eshop.systemtest.e2etests.v7.base;

import com.optivem.eshop.systemtest.base.v7.BaseScenarioDslTest;
import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;

public class BaseE2eTest extends BaseScenarioDslTest {

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}


