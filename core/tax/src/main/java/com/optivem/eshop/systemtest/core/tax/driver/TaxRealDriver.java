package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.client.TaxRealClient;
import com.optivem.eshop.systemtest.core.tax.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public class TaxRealDriver implements TaxDriver {

    private final HttpClient httpClient;
    private final TaxRealClient taxClient;

    public TaxRealDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var taxHttpClient = new TaxHttpClient(httpClient, baseUrl);
        this.taxClient = new TaxRealClient(taxHttpClient);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }

    @Override
    public Result<Void, TaxError> goToTax() {
        return taxClient.health().checkHealth();
    }

    @Override
    public Result<Void, TaxError> returnsTaxRate(ReturnsTaxRateRequest request) {
        // No-op for real driver - data already exists in real service
        return Result.success();
    }

    @Override
    public Result<GetTaxResponse, TaxError> getTax(GetTaxRequest request) {
        return taxClient.countries().getCountry(request.getCountry())
                .map(taxDetails -> GetTaxResponse.builder()
                        .country(taxDetails.getId())
                        .taxRate(taxDetails.getTaxRate())
                        .build());
    }
}
