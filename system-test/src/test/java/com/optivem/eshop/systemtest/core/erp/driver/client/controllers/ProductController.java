package com.optivem.eshop.systemtest.core.erp.driver.client.controllers;

import com.optivem.eshop.systemtest.core.common.error.Error;
import com.optivem.eshop.systemtest.core.common.error.ProblemDetailConverter;
import com.optivem.eshop.systemtest.core.common.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.CreateProductRequest;
import com.optivem.lang.Result;

public class ProductController {

    private static final String ENDPOINT = "/api/products";

    private final JsonHttpClient<ProblemDetailResponse> httpClient;

    public ProductController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, Error> createProduct(CreateProductRequest request) {
        return httpClient.post(ENDPOINT, request)
                .mapFailure(ProblemDetailConverter::toError);
    }
}
