package com.optivem.eshop.systemtest.core.erp.driver.real;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.GetProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.responses.GetProductResponse;
import com.optivem.eshop.systemtest.core.erp.client.real.ErpRealClient;
import com.optivem.eshop.systemtest.core.erp.client.real.dtos.requests.CreateProductRequest;
import com.optivem.lang.Closer;
import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public class ErpRealDriver implements ErpDriver {

    private static final String DEFAULT_TITLE = "Test Product Title";
    private static final String DEFAULT_DESCRIPTION = "Test Product Description";
    private static final String DEFAULT_CATEGORY = "Test Category";
    private static final String DEFAULT_BRAND = "Test Brand";

    private final ErpRealClient erpClient;

    public ErpRealDriver(String baseUrl) {
        this.erpClient = new ErpRealClient(baseUrl);
    }

    @Override
    public Result<Void, Error> goToErp() {
        return erpClient.health().checkHealth();
    }

    @Override
    public Result<Void, Error> returnsProduct(ReturnsProductRequest request) {
        var createProductRequest = CreateProductRequest.builder()
                .id(request.getSku())
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .category(DEFAULT_CATEGORY)
                .brand(DEFAULT_BRAND)
                .price(request.getPrice())
                .build();

        return erpClient.products().createProduct(createProductRequest);
    }

    @Override
    public Result<GetProductResponse, Error> getProduct(GetProductRequest request) {
        return erpClient.products().getProduct(request.getSku())
                .map(productDetails -> GetProductResponse.builder()
                        .sku(productDetails.getId())
                        .price(productDetails.getPrice())
                        .build());
    }

    @Override
    public void close() {
        Closer.close(erpClient);
    }
}

