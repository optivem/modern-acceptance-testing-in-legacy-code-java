package com.optivem.eshop.systemtest.driver.core.tax.driver;

import com.optivem.eshop.systemtest.driver.core.tax.client.TaxRealClient;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.error.TaxErrorResponse;
import com.optivem.commons.util.Result;

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

