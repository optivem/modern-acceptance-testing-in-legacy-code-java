package com.optivem.eshop.systemtest.core.erp.driver.client.controllers;

import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.CreateProductRequest;
import com.optivem.lang.Error;
import com.optivem.lang.Result;

public class ProductController {

    private static final String ENDPOINT = "/api/products";

    private final JsonHttpClient httpClient;

    public ProductController(JsonHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, Error> createProduct(CreateProductRequest request) {
        return httpClient.post(ENDPOINT, request, Void.class)
                .mapFailure(JsonHttpClient::convertProblemDetailToError);
    }
}
