package com.optivem.eshop.systemtest.core.erp.client.base;

import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.client.base.controllers.HealthController;
import com.optivem.eshop.systemtest.core.erp.client.base.controllers.ProductController;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Closer;

import java.net.http.HttpClient;
import java.util.function.Function;

/**
 * Base ERP client with common endpoints shared between real and stub implementations.
 */
public abstract class BaseErpClient<T extends ProductController> implements AutoCloseable {

    private final HttpClient httpClient;

    private final HealthController healthController;
    private final T productController;

    protected BaseErpClient(String baseUrl,
            Function<JsonHttpClient<ProblemDetailResponse>, T> productControllerFactory) {
        this.httpClient = HttpClient.newHttpClient();
        var jsonHttpClient = new JsonHttpClient<>(httpClient, baseUrl, ProblemDetailResponse.class);

        this.healthController = new HealthController(jsonHttpClient);
        this.productController = productControllerFactory.apply(jsonHttpClient);
    }

    public HealthController health() {
        return healthController;
    }

    public T products() {
        return productController;
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}
