package com.optivem.eshop.systemtest.core.commons.clients;

import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;

public abstract class BaseController<E> {
    protected final JsonHttpClient<E> httpClient;

    protected BaseController(JsonHttpClient<E> httpClient) {
        this.httpClient = httpClient;
    }
}
