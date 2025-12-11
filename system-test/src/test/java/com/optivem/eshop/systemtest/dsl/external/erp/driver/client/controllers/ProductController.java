package com.optivem.eshop.systemtest.dsl.external.erp.driver.client.controllers;

import com.optivem.http.HttpGateway;
import com.optivem.http.HttpUtils;
import com.optivem.eshop.systemtest.dsl.external.erp.dtos.CreateProductRequest;
import com.optivem.results.Result;

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

        return HttpUtils.getCreatedResultOrFailure(httpResponse);
    }
}
