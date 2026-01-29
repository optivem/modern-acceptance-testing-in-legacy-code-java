package com.optivem.eshop.systemtest.core.tax.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.commons.http.HttpStatus;
import com.optivem.eshop.systemtest.core.tax.client.dtos.ExtCountryDetailsResponse;
import com.optivem.eshop.systemtest.core.tax.client.dtos.error.ExtTaxErrorResponse;
import com.optivem.commons.util.Result;
import com.optivem.commons.wiremock.JsonWireMockClient;

import java.net.URI;

public class TaxStubClient extends BaseTaxClient {

    private final JsonWireMockClient wireMockClient;

    public TaxStubClient(String baseUrl) {
        super(baseUrl);
        this.wireMockClient = new JsonWireMockClient(baseUrl);
    }

    public Result<Void, ExtTaxErrorResponse> configureGetCountry(ExtCountryDetailsResponse response) {
        var country = response.getId();
        return wireMockClient.stubGet("/tax/api/countries/" + country, HttpStatus.OK, response)
                .mapError(ExtTaxErrorResponse::new);
    }
}

