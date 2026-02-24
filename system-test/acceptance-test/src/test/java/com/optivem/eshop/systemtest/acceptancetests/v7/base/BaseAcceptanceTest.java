package com.optivem.eshop.systemtest.acceptancetests.v7.base;

import com.optivem.eshop.systemtest.base.v7.BaseScenarioDslTest;
import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;

public class BaseAcceptanceTest extends BaseScenarioDslTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }
}

