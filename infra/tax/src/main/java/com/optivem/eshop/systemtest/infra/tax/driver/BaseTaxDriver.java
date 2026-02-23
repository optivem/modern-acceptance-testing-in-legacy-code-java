package com.optivem.eshop.systemtest.infra.tax.driver;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;

import com.optivem.eshop.systemtest.infra.tax.client.BaseTaxClient;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;

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
