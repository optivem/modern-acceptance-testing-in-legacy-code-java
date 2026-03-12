package com.optivem.eshop.systemtest.v7.e2e.base;

import com.optivem.eshop.systemtest.v7.base.BaseScenarioDslTest;
import com.optivem.eshop.dsl.port.ExternalSystemMode;

public class BaseE2eTest extends BaseScenarioDslTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}




