package com.optivem.eshop.systemtest.driver.adapter.erp;

import com.optivem.eshop.systemtest.driver.port.erp.ErpDriver;

import com.optivem.eshop.systemtest.driver.adapter.erp.client.BaseErpClient;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.error.ErpErrorResponse;
import com.optivem.common.Closer;
import com.optivem.common.Result;

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
        return client.checkHealth()
                .mapError(ext -> ErpErrorResponse.builder().message(ext.getMessage()).build());
    }

    @Override
    public Result<GetProductResponse, ErpErrorResponse> getProduct(GetProductRequest request) {
        return client.getProduct(request.getSku())
                .map(productDetails -> GetProductResponse.builder()
                        .sku(productDetails.getId())
                        .price(productDetails.getPrice())
                        .build())
                .mapError(ext -> ErpErrorResponse.builder().message(ext.getMessage()).build());
    }
}

