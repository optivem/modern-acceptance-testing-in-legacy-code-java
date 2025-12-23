package com.optivem.eshop.systemtest.core.erp.driver;

import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.lang.Result;

public interface ErpDriver extends AutoCloseable {
    Result<Void, ErpErrorResponse> goToErp();

    Result<Void, ErpErrorResponse> returnsProduct(ReturnsProductRequest request);

    Result<GetProductResponse, ErpErrorResponse> getProduct(GetProductRequest request);
}
