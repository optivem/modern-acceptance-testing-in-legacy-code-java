package com.optivem.eshop.systemtest.core.erp.client.stub;

import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.client.base.BaseErpClient;
import com.optivem.eshop.systemtest.core.erp.client.base.controllers.ProductController;
import com.optivem.http.JsonHttpClient;

/**
 * Stub ERP client for making HTTP calls to the WireMock stub.
 */
public class ErpStubClient extends BaseErpClient<ProductController> {

    public ErpStubClient(String baseUrl) {
        super(baseUrl, ProductController::new);
    }
}

