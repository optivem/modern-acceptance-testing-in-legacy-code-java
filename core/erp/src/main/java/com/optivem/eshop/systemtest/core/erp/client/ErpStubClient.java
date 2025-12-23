package com.optivem.eshop.systemtest.core.erp.client;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.lang.Result;
import com.optivem.wiremock.JsonWireMockClient;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

/**
 * Stub ERP client for making HTTP calls to the WireMock stub.
 */
public class ErpStubClient extends BaseErpClient {

    private final WireMock wireMock;
    private final JsonWireMockClient wireMockClient;

    public ErpStubClient(String baseUrl) {
        super(baseUrl);

        // Parse the base URL to extract host and port for WireMock admin API
        var url = URI.create(baseUrl);
        this.wireMock = new WireMock(url.getHost(), url.getPort());
        this.wireMockClient = new JsonWireMockClient(wireMock);
    }

    public void configureGetProduct(ExtProductDetailsResponse response) {
        var sku = response.getId();
        wireMockClient.configureGet("/erp/api/products/" + sku, 200, response);
    }
}

