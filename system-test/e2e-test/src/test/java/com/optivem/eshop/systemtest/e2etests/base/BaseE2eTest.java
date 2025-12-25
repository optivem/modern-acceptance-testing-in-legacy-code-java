package com.optivem.eshop.systemtest.e2etests.base;

import com.optivem.eshop.systemtest.base.v5.BaseSystemTest;
import com.optivem.testing.dsl.ExternalSystemMode;

public class BaseE2eTest extends BaseSystemTest {
    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.REAL;
    }
}
