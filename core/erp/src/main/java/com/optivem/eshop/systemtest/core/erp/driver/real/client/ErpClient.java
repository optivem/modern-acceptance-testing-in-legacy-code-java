package com.optivem.eshop.systemtest.core.erp.driver.real.client;

import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.driver.real.client.controllers.ProductController;

public class ErpClient {

    private final ProductController productController;

    public ErpClient(JsonHttpClient<ProblemDetailResponse> httpGateway) {
        this.productController = new ProductController(httpGateway);
    }

    public ProductController products() {
        return productController;
    }
}
