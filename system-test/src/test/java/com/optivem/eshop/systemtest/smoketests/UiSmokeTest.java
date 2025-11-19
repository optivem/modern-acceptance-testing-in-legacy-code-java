package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.clients.ClientCloser;
import com.optivem.eshop.systemtest.core.clients.ClientFactory;
import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;
import org.junit.jupiter.api.*;

public class UiSmokeTest {

    private ShopUiClient shopUiClient;

    @BeforeEach
    void setUp() {
        this.shopUiClient = ClientFactory.createShopUiClient();
    }

    @AfterEach
    void tearDown() {
        ClientCloser.close(shopUiClient);
    }

    @Test
    void home_shouldReturnHtmlContent() {
        shopUiClient.openHomePage();
        shopUiClient.assertHomePageLoaded();
    }
}
