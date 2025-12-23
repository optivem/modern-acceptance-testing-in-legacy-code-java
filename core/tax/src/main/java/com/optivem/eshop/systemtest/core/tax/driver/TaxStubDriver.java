package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.eshop.systemtest.core.tax.client.TaxStubClient;
import com.optivem.eshop.systemtest.core.tax.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.net.URI;
import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TaxStubDriver extends BaseTaxDriver<TaxStubClient> {
    private final WireMock wireMock;

    public TaxStubDriver(String baseUrl) {
        super(new TaxStubClient(baseUrl));

        var url = URI.create(baseUrl);
        var host = url.getHost();
        var port = url.getPort();

        this.wireMock = new WireMock(host, port);
    }

    @Override
    public Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request) {
        var country = request.getCountry();
        var taxRate = request.getTaxRate();

        // Stub GET request for the tax rate
        wireMock.register(get(urlPathEqualTo("/tax/api/countries/" + country))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(String.format(
                                "{\"id\":\"%s\",\"countryName\":\"%s\",\"taxRate\":%s}",
                                country, request.getCountry(), taxRate))));

        // TODO: VJ: Should make Dto for request body

        return Result.success();
    }
}
