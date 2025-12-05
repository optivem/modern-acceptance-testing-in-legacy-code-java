package com.optivem.eshop.systemtest.core.drivers.external.erp.api.client.controllers;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.HttpGateway;
import com.optivem.eshop.systemtest.core.drivers.commons.clients.TestHttpUtils;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.client.dtos.CreateProductRequest;
import com.optivem.eshop.systemtest.core.drivers.commons.Result;

public class ProductController {

    private static final String ENDPOINT = "/api/products";

    private final HttpGateway httpClient;

    public ProductController(HttpGateway httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void> createProduct(String sku, String price) {
        var request = new CreateProductRequest();
        request.setId(sku);
        request.setTitle("Test product title for " + sku);
        request.setDescription("Test product description for " + sku);
        request.setPrice(price);
        request.setCategory("Test Category");
        request.setBrand("Test Brand");

        var httpResponse = httpClient.post(ENDPOINT, request);

        return TestHttpUtils.getCreatedResultOrFailure(httpResponse);
    }
}
