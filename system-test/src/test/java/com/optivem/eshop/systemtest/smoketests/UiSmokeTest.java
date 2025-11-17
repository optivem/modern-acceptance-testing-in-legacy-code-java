package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.core.clients.ui.UiClient;
import org.junit.jupiter.api.*;

public class UiSmokeTest {

    private UiClient uiClient;

    @BeforeEach
    void setUp() {
        var baseUrl = TestConfiguration.getBaseUrl();
        this.uiClient = new UiClient(baseUrl);
    }

    @AfterEach
    void tearDown() {
        if (uiClient != null) {
            uiClient.close();
        }
    }

    @Test
    void home_shouldReturnHtmlContent() {
        uiClient.openHomePage();
        uiClient.assertHomePageLoaded();
    }
}
