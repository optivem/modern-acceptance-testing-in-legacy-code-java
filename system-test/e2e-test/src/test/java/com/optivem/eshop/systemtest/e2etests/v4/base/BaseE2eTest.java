package com.optivem.eshop.systemtest.e2etests.v4.base;

import com.optivem.eshop.systemtest.base.v4.BaseChannelDriverTest;
import com.optivem.eshop.systemtest.configuration.Environment;
import com.optivem.commons.dsl.ExternalSystemMode;

public abstract class BaseE2eTest extends BaseChannelDriverTest {

    @Override
    protected Environment getFixedEnvironment() {
        return Environment.LOCAL;
    }

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}
