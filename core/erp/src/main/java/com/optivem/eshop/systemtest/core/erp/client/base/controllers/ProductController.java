package com.optivem.eshop.systemtest.core.erp.client.base.controllers;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailConverter;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.client.base.controllers.base.BaseController;
import com.optivem.eshop.systemtest.core.erp.client.base.dtos.responses.ExternalProductDetailsResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

/**
 * Product controller for retrieving product details from ERP.
 * Shared between real and stub implementations.
 */
public class ProductController extends BaseController {

    private static final String ENDPOINT = "/api/products";

    public ProductController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        super(httpClient);
    }

    public Result<ExternalProductDetailsResponse, Error> getProduct(String sku) {
        return httpClient.get(ENDPOINT + "/" + sku, ExternalProductDetailsResponse.class)
                .mapError(ProblemDetailConverter::toError);
    }
}

