package com.optivem.eshop.systemtest.core.erp.driver.real;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.real.client.ErpRealClient;
import com.optivem.eshop.systemtest.core.erp.driver.real.dtos.requests.CreateProductRequest;
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

    private final HttpClient httpClient;
    private final ErpRealClient erpClient;

    public ErpRealDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var httpGateway = new JsonHttpClient<>(httpClient, baseUrl, ProblemDetailResponse.class);
        this.erpClient = new ErpRealClient(httpGateway);
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
    public void close() {
        Closer.close(httpClient);
    }


}

