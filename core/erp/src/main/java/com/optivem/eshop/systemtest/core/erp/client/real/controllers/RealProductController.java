package com.optivem.eshop.systemtest.core.erp.client.real.controllers;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailConverter;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.client.base.controllers.ProductController;
import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.erp.client.real.dtos.requests.CreateProductRequest;
import com.optivem.lang.Result;

public class RealProductController extends ProductController {

    private static final String ENDPOINT = "/api/products";

    public RealProductController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        super(httpClient);
    }

    public Result<Void, Error> createProduct(CreateProductRequest request) {
        return httpClient.post(ENDPOINT, request)
                .mapError(ProblemDetailConverter::toError);
    }
}
