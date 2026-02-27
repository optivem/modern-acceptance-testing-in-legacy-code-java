package com.optivem.eshop.systemtest.driver.core.erp.client;


import com.optivem.eshop.systemtest.driver.core.shared.http.HttpStatus;
import com.optivem.eshop.systemtest.driver.core.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.eshop.systemtest.driver.core.erp.client.dtos.error.ExtErpErrorResponse;
import com.optivem.common.Result;
import com.optivem.eshop.systemtest.driver.core.shared.wiremock.JsonWireMockClient;


public class ErpStubClient extends BaseErpClient {
    private static final String ERP_PRODUCTS_ENDPOINT = "/erp/api/products";

    private final JsonWireMockClient wireMockClient;

    public ErpStubClient(String baseUrl) {
        super(baseUrl);
        this.wireMockClient = new JsonWireMockClient(baseUrl);
    }

    public Result<Void, ExtErpErrorResponse> configureGetProduct(ExtProductDetailsResponse response) {
        var sku = response.getId();
        return wireMockClient.stubGet(ERP_PRODUCTS_ENDPOINT + "/" + sku, HttpStatus.OK, response)
                .mapError(ExtErpErrorResponse::new);
    }
}


