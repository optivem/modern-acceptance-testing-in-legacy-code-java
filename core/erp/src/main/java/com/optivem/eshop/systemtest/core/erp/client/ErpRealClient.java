package com.optivem.eshop.systemtest.core.erp.client;

import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtProductDetailsRequest;
import com.optivem.eshop.systemtest.core.erp.client.dtos.error.ExtErpErrorResponse;
import com.optivem.commons.util.Result;

public class ErpRealClient extends BaseErpClient {

    public ErpRealClient(String baseUrl) {
        super(baseUrl);
    }

    public Result<Void, ExtErpErrorResponse> createProduct(ExtProductDetailsRequest request) {
        return httpClient.post("/api/products", request);
    }
}

