package com.optivem.eshop.systemtest.core.erp.driver;

import com.optivem.eshop.systemtest.core.erp.client.BaseErpClient;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;

public abstract class BaseErpDriver<TClient extends BaseErpClient> implements ErpDriver {

    protected final TClient client;

    protected BaseErpDriver(TClient client) {
        this.client = client;
    }

    @Override
    public void close() throws Exception {
        Closer.close(client);
    }

    @Override
    public Result<Void, ErpErrorResponse> goToErp() {
        return client.checkHealth().mapError(ErpErrorResponse::from);
    }

    @Override
    public Result<GetProductResponse, ErpErrorResponse> getProduct(GetProductRequest request) {
        return client.getProduct(request.getSku())
                .map(productDetails -> GetProductResponse.builder()
                        .sku(productDetails.getId())
                        .price(productDetails.getPrice())
                        .build())
                .mapError(ErpErrorResponse::from);
    }
}
