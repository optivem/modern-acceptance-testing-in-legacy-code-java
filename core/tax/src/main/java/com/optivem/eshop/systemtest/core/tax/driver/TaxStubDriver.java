package com.optivem.eshop.systemtest.core.tax.driver;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.eshop.systemtest.core.tax.client.TaxStubClient;
import com.optivem.eshop.systemtest.core.tax.client.dtos.ExtCountryDetailsResponse;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.lang.Result;

import java.math.BigDecimal;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TaxStubDriver extends BaseTaxDriver<TaxStubClient> {

    public TaxStubDriver(String baseUrl) {
        super(new TaxStubClient(baseUrl));
    }

    @Override
    public Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request) {
        var country = request.getCountry();
        var taxRate = new BigDecimal(request.getTaxRate());

        var response = ExtCountryDetailsResponse.builder()
                .id(country)
                .taxRate(taxRate)
                .countryName(country)
                .build();

        client.configureGetCountry(response);

        return getTaxRate(country)
                .mapVoid();
    }
}
