package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.ShopDsl;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.eshop.systemtest.core.channels.ChannelType;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

@ExtendWith(ChannelExtension.class)
public class ShopSmokeTest {

    private ShopDriver shopDriver;
    private ShopDsl shop;

    @BeforeEach
    void setUp() {
        this.shopDriver = DriverFactory.createShopDriver();
        var context = new DslContext();
        shop = new ShopDsl(shopDriver, context);
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        shop.goToShop();
        shop.confirmShopOpened();
    }
}
