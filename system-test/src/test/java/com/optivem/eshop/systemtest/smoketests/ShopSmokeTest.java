package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.DslFactory;
import com.optivem.eshop.systemtest.dsl.Dsl;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.eshop.systemtest.dsl.shop.ChannelType;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class ShopSmokeTest {

    private Dsl dsl;

    @BeforeEach
    void setUp() {
        dsl = DslFactory.create();
    }

    @AfterEach
    void tearDown() {
        Closer.close(dsl);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        dsl.shop().goToShop()
                .execute()
                .shouldSucceed();
    }
}
