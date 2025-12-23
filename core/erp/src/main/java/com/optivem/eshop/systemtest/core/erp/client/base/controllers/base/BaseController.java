package com.optivem.eshop.systemtest.core.erp.client.base.controllers.base;

import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;

public abstract class BaseController {
    protected final JsonHttpClient<ProblemDetailResponse> httpClient;

    protected BaseController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }
}
