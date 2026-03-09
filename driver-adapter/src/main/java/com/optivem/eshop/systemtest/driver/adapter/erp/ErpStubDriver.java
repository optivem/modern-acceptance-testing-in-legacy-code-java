package com.optivem.eshop.systemtest.driver.adapter.erp;

import com.optivem.eshop.systemtest.driver.adapter.erp.client.ErpStubClient;
import com.optivem.eshop.systemtest.driver.adapter.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.driver.port.shared.dtos.ErrorResponse;
import com.optivem.common.Converter;
import com.optivem.common.Result;

/**
 * ErpStubDriver uses WireMock to stub ERP API responses.
 * This allows tests to run without a real ERP system.
 */
public class ErpStubDriver extends BaseErpDriver<ErpStubClient> {
    public ErpStubDriver(String baseUrl) {
        super(new ErpStubClient(baseUrl));
    }

    @Override
    public Result<Void, ErrorResponse> returnsProduct(ReturnsProductRequest request) {
        if (request.getReviewable() != null) {
            throw new UnsupportedOperationException("Driver skeleton");
        }

        var extProductDetailsResponse = ExtProductDetailsResponse.builder()
                .id(request.getSku())
                .title("")
                .description("")
                .price(Converter.toBigDecimal(request.getPrice()))
                .category("")
                .brand("")
                .build();

        return client.configureGetProduct(extProductDetailsResponse)
                .mapError(ext -> ErrorResponse.builder().message(ext.getMessage()).build());
    }
}

