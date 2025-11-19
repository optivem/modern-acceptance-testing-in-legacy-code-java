package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.clients.ClientCloser;
import com.optivem.eshop.systemtest.core.clients.ClientFactory;
import com.optivem.eshop.systemtest.core.clients.external.tax.TaxApiClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxApiSmokeTest {

    private TaxApiClient taxApiClient;

    @BeforeEach
    void setUp() {
        this.taxApiClient = ClientFactory.createTaxApiClient();
    }

    @AfterEach
    void tearDown() {
        ClientCloser.close(taxApiClient);
    }

    @Test
    void home_shouldReturn200OK() {
        // Arrange & Act
        var httpResponse = taxApiClient.home().home();

        // Assert
        taxApiClient.home().assertHomeSuccessful(httpResponse);
    }
}

