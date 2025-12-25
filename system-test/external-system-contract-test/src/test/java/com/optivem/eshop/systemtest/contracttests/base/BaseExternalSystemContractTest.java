package com.optivem.eshop.systemtest.contracttests.base;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import com.optivem.testing.dsl.ExternalSystemMode;

public abstract class BaseExternalSystemContractTest extends BaseSystemDslTest {
    protected abstract ExternalSystemMode getFixedExternalSystemMode();
}
