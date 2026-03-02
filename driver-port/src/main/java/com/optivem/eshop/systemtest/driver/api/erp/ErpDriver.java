package com.optivem.eshop.systemtest.driver.port.erp;

import com.optivem.eshop.systemtest.driver.port.erp.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.error.ErpErrorResponse;
import com.optivem.common.Result;

public interface ErpDriver extends AutoCloseable {
    Result<Void, ErpErrorResponse> goToErp();

    Result<GetProductResponse, ErpErrorResponse> getProduct(GetProductRequest request);

    Result<Void, ErpErrorResponse> returnsProduct(ReturnsProductRequest request);
}

