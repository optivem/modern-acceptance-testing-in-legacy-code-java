package com.optivem.eshop.systemtest.driver.core.erp.client;

import com.optivem.eshop.systemtest.driver.core.erp.client.dtos.ExtCreateProductRequest;
import com.optivem.eshop.systemtest.driver.core.erp.client.dtos.error.ExtErpErrorResponse;
import com.optivem.common.Result;

public class ErpRealClient extends BaseErpClient {
    private static final String PRODUCTS_ENDPOINT = "/api/products";

    public ErpRealClient(String baseUrl) {
        super(baseUrl);
    }

    public Result<Void, ExtErpErrorResponse> createProduct(ExtCreateProductRequest request) {
        return httpClient.post(PRODUCTS_ENDPOINT, request);
    }
}

