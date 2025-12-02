package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.atdd.commons.channels.Channel;
import com.optivem.atdd.commons.channels.ChannelExtension;
import com.optivem.eshop.systemtest.core.drivers.ChannelType;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.commons.clients.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.optivem.eshop.systemtest.core.drivers.commons.ResultAssert.assertThatResult;

@ExtendWith(ChannelExtension.class)
public class ShopSmokeTest {

    private ShopDriver shopDriver;

    @BeforeEach
    void setUp() {
        shopDriver = DriverFactory.createShopDriver();
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
    }

    @Channel({ChannelType.UI, ChannelType.API})
    @TestTemplate
    void shouldBeAbleToGoToShop() {
        var result = shopDriver.goToShop();
        assertThatResult(result).isSuccess();
    }
}
