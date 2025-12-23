package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.tax.client.BaseTaxClient;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;

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
        return client.checkHealth().mapError(TaxErrorResponse::from);
    }

    @Override
    public Result<GetTaxResponse, TaxErrorResponse> getTaxRate(GetTaxRequest request) {
        var country = request.getCountry();
        return client.getCountry(country)
                .map(taxRateResponse -> GetTaxResponse.builder()
                        .country(taxRateResponse.getId())
                        .taxRate(taxRateResponse.getTaxRate())
                        .build())
                .mapError(TaxErrorResponse::from);
    }

    public abstract Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request);
}
