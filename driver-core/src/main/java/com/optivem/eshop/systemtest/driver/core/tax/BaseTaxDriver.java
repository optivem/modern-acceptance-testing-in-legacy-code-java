package com.optivem.eshop.systemtest.driver.core.tax;

import com.optivem.eshop.systemtest.driver.api.tax.TaxDriver;

import com.optivem.eshop.systemtest.driver.core.tax.client.BaseTaxClient;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.error.TaxErrorResponse;
import com.optivem.common.util.Closer;
import com.optivem.common.util.Result;

public abstract class BaseTaxDriver<TClient extends BaseTaxClient> implements TaxDriver {
    protected final TClient client;

    protected BaseTaxDriver(TClient client) {
        this.client = client;
    }

    @Override
    public void close() {
        Closer.close(client);
    }

    @Override
    public Result<Void, TaxErrorResponse> goToTax() {
        return client.checkHealth()
                .mapError(ext -> TaxErrorResponse.builder().message(ext.getMessage()).build());
    }

    @Override
    public Result<GetTaxResponse, TaxErrorResponse> getTaxRate(String country) {
        return client.getCountry(country)
                .map(taxRateResponse -> GetTaxResponse.builder()
                        .country(taxRateResponse.getId())
                        .taxRate(taxRateResponse.getTaxRate())
                        .build())
                .mapError(ext -> TaxErrorResponse.builder().message(ext.getMessage()).build());
    }
}

