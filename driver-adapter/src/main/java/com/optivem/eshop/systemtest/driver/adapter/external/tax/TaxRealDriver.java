package com.optivem.eshop.systemtest.driver.adapter.external.tax;

import com.optivem.eshop.systemtest.driver.adapter.external.tax.client.TaxRealClient;
import com.optivem.eshop.systemtest.driver.port.external.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.driver.port.shared.dtos.ErrorResponse;
import com.optivem.common.Result;

public class TaxRealDriver extends BaseTaxDriver<TaxRealClient> {
    public TaxRealDriver(String baseUrl) {
        super(new TaxRealClient(baseUrl));
    }

    @Override
    public Result<Void, ErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request) {
        // No-op for real driver - data already exists in real service
        return Result.success();
    }
}
