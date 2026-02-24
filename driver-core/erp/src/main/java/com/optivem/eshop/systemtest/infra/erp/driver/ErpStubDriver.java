package com.optivem.eshop.systemtest.infra.erp.driver;

import com.optivem.eshop.systemtest.infra.erp.client.ErpStubClient;
import com.optivem.eshop.systemtest.infra.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.commons.util.Converter;
import com.optivem.commons.util.Result;

/**
 * ErpStubDriver uses WireMock to stub ERP API responses.
 * This allows tests to run without a real ERP system.
 */
public class ErpStubDriver extends BaseErpDriver<ErpStubClient> {

    public ErpStubDriver(String baseUrl) {
        super(new ErpStubClient(baseUrl));
    }

    @Override
    public Result<Void, ErpErrorResponse> returnsProduct(ReturnsProductRequest request) {
        var extProductDetailsResponse = ExtProductDetailsResponse.builder()
                .id(request.getSku())
                .title("")
                .description("")
                .price(Converter.toBigDecimal(request.getPrice()))
                .category("")
                .brand("")
                .build();

        return client.configureGetProduct(extProductDetailsResponse)
                .mapError(ext -> ErpErrorResponse.builder().message(ext.getMessage()).build());
    }
}
