package com.optivem.eshop.systemtest.core.tax.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.eshop.systemtest.core.tax.client.dtos.ExtCountryDetailsResponse;
import com.optivem.eshop.systemtest.core.tax.client.dtos.error.ExtTaxErrorResponse;
import com.optivem.lang.Result;
import com.optivem.wiremock.JsonWireMockClient;

import java.net.URI;

public class TaxStubClient extends BaseTaxClient {

    private final WireMock wireMock;
    private final JsonWireMockClient wireMockClient;

    public TaxStubClient(String baseUrl) {
        super(baseUrl);

        var url = URI.create(baseUrl);
        this.wireMock = new WireMock(url.getHost(), url.getPort());
        this.wireMockClient = new JsonWireMockClient(wireMock);
    }

    public Result<Void, ExtTaxErrorResponse> configureGetCountry(ExtCountryDetailsResponse response) {
        var country = response.getId();
        return wireMockClient.stubGet("/tax/api/countries/" + country, 200, response)
                .mapError(ExtTaxErrorResponse::new);
    }
}

