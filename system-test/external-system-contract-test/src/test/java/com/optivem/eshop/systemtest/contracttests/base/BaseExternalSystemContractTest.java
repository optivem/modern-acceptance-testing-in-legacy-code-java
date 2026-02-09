package com.optivem.eshop.systemtest.contracttests.base;

import com.optivem.eshop.systemtest.base.v5.BaseSystemDslTest;
import com.optivem.commons.dsl.ExternalSystemMode;

public abstract class BaseExternalSystemContractTest extends BaseSystemDslTest {

    @Override
    protected abstract ExternalSystemMode getFixedExternalSystemMode();
}
