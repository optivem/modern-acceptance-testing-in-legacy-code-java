package com.optivem.eshop.systemtest.contracttests.v7.base;

import com.optivem.eshop.systemtest.base.v5.BaseAppDslTest;
import com.optivem.eshop.systemtest.dsl.core.app.shared.ExternalSystemMode;
import com.optivem.eshop.systemtest.dsl.core.scenario.ScenarioDslImpl;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseExternalSystemContractTest extends BaseAppDslTest {
    protected ScenarioDslImpl scenario;

    @BeforeEach
    void setUpScenario() {
        scenario = new ScenarioDslImpl(app);
    }

    @Override
    protected abstract ExternalSystemMode getFixedExternalSystemMode();
}