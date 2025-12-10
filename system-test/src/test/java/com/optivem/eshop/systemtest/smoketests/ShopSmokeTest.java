package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.dsl.DslFactory;
import com.optivem.eshop.systemtest.core.dsl.shop.ShopDsl;
import com.optivem.testing.channel.Channel;
import com.optivem.testing.channel.ChannelExtension;
import com.optivem.eshop.systemtest.core.channels.ChannelType;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class ShopSmokeTest {

    private ShopDsl shop;

    @BeforeEach
    void setUp() {
        DslFactory dslFactory = new DslFactory();
        shop = dslFactory.createShopDsl();
    }

    @AfterEach
    void tearDown() {
        Closer.close(shop);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        shop.goToShop()
                .execute()
                .shouldSucceed();
    }
}
