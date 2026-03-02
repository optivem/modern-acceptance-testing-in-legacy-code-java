package com.optivem.eshop.systemtest.driver.adapter.tax;

import com.optivem.eshop.systemtest.driver.adapter.tax.client.TaxRealClient;
import com.optivem.eshop.systemtest.driver.port.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.driver.port.tax.dtos.error.TaxErrorResponse;
import com.optivem.common.Result;

public class TaxRealDriver extends BaseTaxDriver<TaxRealClient> {
    public TaxRealDriver(String baseUrl) {
        super(new TaxRealClient(baseUrl));
    }

    @Override
    public Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request) {
        // No-op for real driver - data already exists in real service
        return Result.success();
    }
}

