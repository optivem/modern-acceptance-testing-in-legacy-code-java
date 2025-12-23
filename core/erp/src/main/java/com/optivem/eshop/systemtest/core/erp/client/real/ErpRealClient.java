package com.optivem.eshop.systemtest.core.erp.client.real;

import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.client.base.BaseErpClient;
import com.optivem.eshop.systemtest.core.erp.client.real.controllers.RealProductController;
import com.optivem.http.JsonHttpClient;

/**
 * Real ERP client for making actual HTTP calls to the ERP API.
 */
public class ErpRealClient extends BaseErpClient<RealProductController> {

    public ErpRealClient(String baseUrl) {
        super(baseUrl, RealProductController::new);
    }
}

