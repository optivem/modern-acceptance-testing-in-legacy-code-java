package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.core.clients.api.ApiClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiSmokeTest {

    private ApiClient apiClient;

    @BeforeEach
    void setUp() {
        var baseUrl = TestConfiguration.getBaseUrl();
        this.apiClient = new ApiClient(baseUrl);
    }

    @AfterEach
    void tearDown() {
        if (apiClient != null) {
            apiClient.close();
        }
    }

    @Test
    void echo_shouldReturn200OK() throws Exception {
        var httpResponse = apiClient.getEchoController().echo();
        apiClient.getEchoController().confirmEchoSuccessful(httpResponse);
    }
}