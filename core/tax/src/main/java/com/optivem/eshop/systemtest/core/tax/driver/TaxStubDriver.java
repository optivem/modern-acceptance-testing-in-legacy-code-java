package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.client.TaxStubClient;
import com.optivem.eshop.systemtest.core.tax.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TaxStubDriver implements TaxDriver {

    private final HttpClient httpClient;
    private final WireMock wireMock;
    private final TaxStubClient taxClient;

    public TaxStubDriver(String taxBaseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var taxHttpClient = new TaxHttpClient(httpClient, taxBaseUrl);
        this.taxClient = new TaxStubClient(taxHttpClient);

        var url = java.net.URI.create(taxBaseUrl);
        var host = url.getHost();
        var port = url.getPort();

        this.wireMock = new WireMock(host, port);
    }

    @Override
    public Result<Void, TaxError> goToTax() {
        return taxClient.health().checkHealth();
    }

    @Override
    public Result<Void, TaxError> returnsTaxRate(ReturnsTaxRateRequest request) {
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

    @Override
    public Result<GetTaxResponse, TaxError> getTax(GetTaxRequest request) {
        return taxClient.countries().getCountry(request.getCountry())
                .map(taxDetails -> GetTaxResponse.builder()
                        .country(taxDetails.getId())
                        .taxRate(taxDetails.getTaxRate())
                        .build());
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}
