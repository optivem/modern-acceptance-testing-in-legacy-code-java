package com.optivem.eshop.systemtest.driver.core.tax.client;

import com.optivem.eshop.systemtest.driver.core.shared.http.HttpStatus;
import com.optivem.eshop.systemtest.driver.core.tax.client.dtos.ExtCountryDetailsResponse;
import com.optivem.eshop.systemtest.driver.core.tax.client.dtos.error.ExtTaxErrorResponse;
import com.optivem.commons.util.Result;
import com.optivem.eshop.systemtest.driver.core.shared.wiremock.JsonWireMockClient;


public class TaxStubClient extends BaseTaxClient {
    private static final String COUNTRIES_ENDPOINT = "/tax/api/countries";
    
    private final JsonWireMockClient wireMockClient;

    public TaxStubClient(String baseUrl) {
        super(baseUrl);
        this.wireMockClient = new JsonWireMockClient(baseUrl);
    }

    public Result<Void, ExtTaxErrorResponse> configureGetCountry(ExtCountryDetailsResponse response) {
        var country = response.getId();
        return wireMockClient.stubGet(COUNTRIES_ENDPOINT + "/" + country, HttpStatus.OK, response)
                .mapError(ExtTaxErrorResponse::new);
    }
}


