package com.optivem.eshop.systemtest.e2etests.v4.base;

import com.optivem.eshop.systemtest.base.v4.BaseChannelDriverTest;
import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;

import java.util.UUID;

public abstract class BaseE2eTest extends BaseChannelDriverTest {

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }

    protected String createUniqueSku(String baseSku) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return baseSku + "-" + suffix;
    }
}
