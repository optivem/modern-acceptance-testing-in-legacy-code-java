package com.optivem.eshop.systemtest.driver.adapter.external.tax.client;

import com.optivem.eshop.systemtest.driver.adapter.shared.client.http.HttpStatus;
import com.optivem.eshop.systemtest.driver.adapter.external.tax.client.dtos.ExtCountryDetailsResponse;
import com.optivem.eshop.systemtest.driver.adapter.external.tax.client.dtos.error.ExtTaxErrorResponse;
import com.optivem.common.Result;
import com.optivem.eshop.systemtest.driver.adapter.shared.client.wiremock.JsonWireMockClient;


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
