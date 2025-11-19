package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.clients.ClientCloser;
import com.optivem.eshop.systemtest.core.clients.ClientFactory;
import com.optivem.eshop.systemtest.core.clients.external.erp.ErpApiClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ErpApiSmokeTest {

    private ErpApiClient erpApiClient;

    @BeforeEach
    void setUp() {
        this.erpApiClient = ClientFactory.createErpApiClient();
    }

    @AfterEach
    void tearDown() {
        ClientCloser.close(erpApiClient);
    }

    @Test
    void home_shouldReturn200OK() {
        // Arrange & Act
        var httpResponse = erpApiClient.home().home();

        // Assert
        erpApiClient.home().assertHomeSuccessful(httpResponse);
    }
}

