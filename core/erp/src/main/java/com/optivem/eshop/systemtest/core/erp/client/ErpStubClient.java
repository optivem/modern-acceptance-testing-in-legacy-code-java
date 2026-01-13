package com.optivem.eshop.systemtest.core.erp.client;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.eshop.systemtest.core.erp.client.dtos.error.ExtErpErrorResponse;
import com.optivem.lang.Result;
import com.optivem.wiremock.JsonWireMockClient;

import java.net.URI;

/**
 * Stub ERP client for making HTTP calls to the WireMock stub.
 */
public class ErpStubClient extends BaseErpClient {

    private final WireMock wireMock;
    private final JsonWireMockClient wireMockClient;

    public ErpStubClient(String baseUrl) {
        super(baseUrl);

        var url = URI.create(baseUrl);
        this.wireMock = new WireMock(url.getHost(), url.getPort());
        this.wireMockClient = new JsonWireMockClient(wireMock);
    }

    public Result<Void, ExtErpErrorResponse> configureGetProduct(ExtProductDetailsResponse response) {
        var sku = response.getId();
        return wireMockClient.stubGet("/erp/api/products/" + sku, 200, response)
                .mapError(ExtErpErrorResponse::new);
    }
}

