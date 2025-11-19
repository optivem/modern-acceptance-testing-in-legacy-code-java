package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.clients.ClientCloser;
import com.optivem.eshop.systemtest.core.clients.ClientFactory;
import com.optivem.eshop.systemtest.core.clients.system.api.ShopApiClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiSmokeTest {

    private ShopApiClient shopApiClient;

    @BeforeEach
    void setUp() {
        this.shopApiClient = ClientFactory.createShopApiClient();
    }

    @AfterEach
    void tearDown() {
        ClientCloser.close(shopApiClient);
    }

    @Test
    void echo_shouldReturn200OK() {
        var httpResponse = shopApiClient.echo().echo();
        shopApiClient.echo().assertEchoSuccessful(httpResponse);
    }
}
