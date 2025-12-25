package com.optivem.eshop.systemtest.smoketests.v3;

import com.optivem.eshop.systemtest.base.v3.BaseDriverTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ShopApiSmokeTest extends ShopBaseSmokeTest {
    @Override
    protected void setShopApiDriver() {
        setUpShopApiDriver();
    }
}

