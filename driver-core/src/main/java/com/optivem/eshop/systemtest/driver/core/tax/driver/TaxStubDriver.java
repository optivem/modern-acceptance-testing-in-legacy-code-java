package com.optivem.eshop.systemtest.driver.core.tax.driver;

import com.optivem.eshop.systemtest.driver.core.tax.client.TaxStubClient;
import com.optivem.eshop.systemtest.driver.core.tax.client.dtos.ExtCountryDetailsResponse;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.error.TaxErrorResponse;
import com.optivem.commons.util.Converter;
import com.optivem.commons.util.Result;

public class TaxStubDriver extends BaseTaxDriver<TaxStubClient> {
    public TaxStubDriver(String baseUrl) {
        super(new TaxStubClient(baseUrl));
    }

    @Override
    public Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request) {
        var country = request.getCountry();
        var taxRate = Converter.toBigDecimal(request.getTaxRate());

        var response = ExtCountryDetailsResponse.builder()
                .id(country)
                .taxRate(taxRate)
                .countryName(country)
                .build();

        return client.configureGetCountry(response)
                .mapError(ext -> TaxErrorResponse.builder().message(ext.getMessage()).build());
    }
}

