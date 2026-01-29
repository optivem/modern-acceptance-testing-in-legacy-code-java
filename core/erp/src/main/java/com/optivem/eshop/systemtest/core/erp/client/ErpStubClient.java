package com.optivem.eshop.systemtest.core.erp.client;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.commons.http.HttpStatus;
import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.eshop.systemtest.core.erp.client.dtos.error.ExtErpErrorResponse;
import com.optivem.commons.util.Result;
import com.optivem.commons.wiremock.JsonWireMockClient;

import java.net.URI;

public class ErpStubClient extends BaseErpClient {

    private final JsonWireMockClient wireMockClient;

    public ErpStubClient(String baseUrl) {
        super(baseUrl);
        this.wireMockClient = new JsonWireMockClient(baseUrl);
    }

    public Result<Void, ExtErpErrorResponse> configureGetProduct(ExtProductDetailsResponse response) {
        var sku = response.getId();
        return wireMockClient.stubGet("/erp/api/products/" + sku, HttpStatus.OK, response)
                .mapError(ExtErpErrorResponse::new);
    }
}

