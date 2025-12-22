package com.optivem.eshop.systemtest.core.erp.driver;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.real.dtos.requests.CreateProductRequest;
import com.optivem.lang.Result;

public interface ErpDriver extends AutoCloseable {
    Result<Void, Error> returnsProduct(ReturnsProductRequest request);
}
