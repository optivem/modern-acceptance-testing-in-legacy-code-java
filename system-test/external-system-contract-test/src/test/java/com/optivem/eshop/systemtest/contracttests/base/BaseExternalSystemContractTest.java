package com.optivem.eshop.systemtest.contracttests.base;

import com.optivem.eshop.systemtest.base.v5.BaseSystemTest;
import com.optivem.testing.dsl.ExternalSystemMode;

public abstract class BaseExternalSystemContractTest extends BaseSystemTest {
    protected abstract ExternalSystemMode getFixedExternalSystemMode();
}
