package com.optivem.eshop.systemtest.smoketests.stub;

import com.optivem.eshop.systemtest.EnvironmentMode;
import com.optivem.eshop.systemtest.SystemDslFactory;
import com.optivem.eshop.systemtest.core.ExternalSystemMode;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class ShopSmokeTest {

    private SystemDsl app;

    @BeforeEach
    void setUp() {
        app = SystemDslFactory.create(EnvironmentMode.ACCEPTANCE, ExternalSystemMode.STUB);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        app.shop().goToShop()
                .execute()
                .shouldSucceed();
    }
    @Test
    void shouldBeAbleToGoToErp() {
        app.erp().goToErp()
                .execute()
                .shouldSucceed();
    }

    @Test
    void shouldBeAbleToGoToTax() {
        app.tax().goToTax()
                .execute()
                .shouldSucceed();
    }
}
