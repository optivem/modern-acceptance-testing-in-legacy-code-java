package com.optivem.eshop.systemtest.core.erp.driver.client.controllers;

import com.optivem.http.HttpGateway;
import com.optivem.http.HttpUtils;
import com.optivem.eshop.systemtest.core.erp.dtos.CreateProductRequest;
import com.optivem.results.Result;

public class ProductController {

    private static final String ENDPOINT = "/api/products";

    private final HttpGateway httpClient;

    public ProductController(HttpGateway httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void> createProduct(CreateProductRequest request) {
        var httpResponse = httpClient.post(ENDPOINT, request);

        return HttpUtils.getCreatedResultOrFailure(httpResponse);
    }
}
