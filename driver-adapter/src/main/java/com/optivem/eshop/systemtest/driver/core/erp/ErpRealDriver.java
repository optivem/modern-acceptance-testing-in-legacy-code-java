package com.optivem.eshop.systemtest.driver.adapter.erp;

import com.optivem.eshop.systemtest.driver.adapter.erp.client.ErpRealClient;
import com.optivem.eshop.systemtest.driver.adapter.erp.client.dtos.ExtCreateProductRequest;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.error.ErpErrorResponse;
import com.optivem.common.Result;

public class ErpRealDriver extends BaseErpDriver<ErpRealClient> {
    private static final String DEFAULT_TITLE = "Test Product Title";
    private static final String DEFAULT_DESCRIPTION = "Test Product Description";
    private static final String DEFAULT_CATEGORY = "Test Category";
    private static final String DEFAULT_BRAND = "Test Brand";

    public ErpRealDriver(String baseUrl) {
        super(new ErpRealClient(baseUrl));
    }

    @Override
    public Result<Void, ErpErrorResponse> returnsProduct(ReturnsProductRequest request) {
        var createProductRequest = ExtCreateProductRequest.builder()
                .id(request.getSku())
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .category(DEFAULT_CATEGORY)
                .brand(DEFAULT_BRAND)
                .price(request.getPrice())
                .build();

        return client.createProduct(createProductRequest)
                .mapError(ext -> ErpErrorResponse.builder().message(ext.getMessage()).build());
    }
}

