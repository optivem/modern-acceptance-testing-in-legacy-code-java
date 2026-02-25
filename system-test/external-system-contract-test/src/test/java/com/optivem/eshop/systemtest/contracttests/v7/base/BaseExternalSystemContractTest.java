package com.optivem.eshop.systemtest.contracttests.v7.base;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;

public abstract class BaseExternalSystemContractTest extends BaseSystemDslTest {
    @Override
    protected abstract ExternalSystemMode getFixedExternalSystemMode();
}

