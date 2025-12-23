package com.optivem.eshop.systemtest.core.erp.driver.stub;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.client.stub.ErpStubClient;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.GetProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.responses.GetProductResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * ErpStubDriver uses WireMock to stub ERP API responses.
 * This allows tests to run without a real ERP system.
 */
public class ErpStubDriver implements ErpDriver {

    private final HttpClient httpClient;
    private final WireMock wireMock;
    private final ErpStubClient erpClient;

    public ErpStubDriver(String baseUrl) {
        // Create HTTP client for making actual health check requests
        this.httpClient = HttpClient.newHttpClient();
        this.erpClient = new ErpStubClient(baseUrl);

        // Parse the base URL to extract host and port for WireMock admin API
        var url = java.net.URI.create(baseUrl);
        this.wireMock = new WireMock(url.getHost(), url.getPort());
    }

    @Override
    public Result<Void, Error> goToErp() {
        return erpClient.health().checkHealth();
    }

    @Override
    public Result<Void, Error> returnsProduct(ReturnsProductRequest request) {
        // Configure WireMock stub for creating a product in ERP
        var sku = request.getSku();
        var price = request.getPrice();

        // Create a stub that matches the product creation request
        wireMock.register(post(urlPathEqualTo("/erp/api/products"))
                .withRequestBody(matchingJsonPath("$.id", equalTo(sku)))
                .withRequestBody(matchingJsonPath("$.price", equalTo(price)))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{}")));

        // Also stub GET request for the product
        wireMock.register(get(urlPathEqualTo("/erp/api/products/" + sku))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(String.format(
                                "{\"id\":\"%s\",\"price\":\"%s\",\"title\":\"Test Product\",\"description\":\"Test Description\",\"category\":\"Test Category\",\"brand\":\"Test Brand\"}",
                                sku, price))));

        return Result.success();
    }

    @Override
    public Result<GetProductResponse, Error> getProduct(GetProductRequest request) {
        return erpClient.products().getProduct(request.getSku())
                .map(productDetails -> GetProductResponse.builder()
                        .sku(productDetails.getId())
                        .price(productDetails.getPrice())
                        .build());
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}
