package com.optivem.eshop.systemtest.driver.api.erp.driver;

import com.optivem.eshop.systemtest.driver.api.erp.driver.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.driver.api.erp.driver.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.driver.api.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.driver.api.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.commons.util.Result;

public interface ErpDriver extends AutoCloseable {
    Result<Void, ErpErrorResponse> goToErp();

    Result<GetProductResponse, ErpErrorResponse> getProduct(GetProductRequest request);

    Result<Void, ErpErrorResponse> returnsProduct(ReturnsProductRequest request);
}
