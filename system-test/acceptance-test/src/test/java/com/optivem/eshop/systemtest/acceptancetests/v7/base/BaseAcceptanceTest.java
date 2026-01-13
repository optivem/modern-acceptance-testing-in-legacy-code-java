package com.optivem.eshop.systemtest.acceptancetests.v7.base;

import com.optivem.eshop.systemtest.base.v7.BaseScenarioDslTest;
import com.optivem.eshop.systemtest.configuration.Environment;
import com.optivem.commons.dsl.ExternalSystemMode;

public class BaseAcceptanceTest extends BaseScenarioDslTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }

    @Override
    protected Environment getFixedEnvironment() {
        return Environment.LOCAL;
    }
}
