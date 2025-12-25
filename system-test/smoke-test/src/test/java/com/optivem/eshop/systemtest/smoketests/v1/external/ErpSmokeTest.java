package com.optivem.eshop.systemtest.smoketests.v1.external;

import com.optivem.eshop.systemtest.base.v1.BaseRawTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErpSmokeTest extends BaseRawTest {
    @BeforeEach
    void setUp() {
        setUpExternalHttpClients();
    }

    @Test
    void shouldBeAbleToGoToErp() throws Exception {
        var uri = URI.create(getErpBaseUrl() + "/health");
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        var response = erpHttpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }
}

