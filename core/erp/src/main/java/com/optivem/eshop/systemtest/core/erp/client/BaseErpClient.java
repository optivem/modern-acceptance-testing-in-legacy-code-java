package com.optivem.eshop.systemtest.core.erp.client;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailConverter;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

/**
 * Base ERP client with common endpoints shared between real and stub implementations.
 */
public abstract class BaseErpClient implements AutoCloseable {

    private final HttpClient rawHttpClient;
    protected final JsonHttpClient<ProblemDetailResponse> httpClient;

    protected BaseErpClient(String baseUrl) {
        this.rawHttpClient = HttpClient.newHttpClient();
        // TODO: VJ: Actually, this is not the response, it's instead some Error
        this.httpClient = new JsonHttpClient<>(rawHttpClient, baseUrl, ProblemDetailResponse.class);
    }

    @Override
    public void close() {
        Closer.close(rawHttpClient);
    }

    public Result<Void, Error> checkHealth() {
        return httpClient.get("/health")
                .mapError(ProblemDetailConverter::toError);
    }

    public Result<ExtProductDetailsResponse, Error> getProduct(String sku) {
        return httpClient.get("/api/products/" + sku, ExtProductDetailsResponse.class)
                .mapError(ProblemDetailConverter::toError);
    }
}
