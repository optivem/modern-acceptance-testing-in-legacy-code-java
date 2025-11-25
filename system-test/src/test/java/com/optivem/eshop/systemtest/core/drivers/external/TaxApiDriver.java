package com.optivem.eshop.systemtest.core.drivers.external;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpUtils;
import com.optivem.eshop.systemtest.core.clients.external.tax.TaxApiClient;
import com.optivem.eshop.systemtest.core.drivers.system.Result;

public class TaxApiDriver implements AutoCloseable {

    private final TaxApiClient taxApiClient;

    public TaxApiDriver(TaxApiClient taxApiClient) {
        this.taxApiClient = taxApiClient;
    }

    public Result<Void> goToTaxation() {
        var httpResponse = taxApiClient.home().home();
        return TestHttpUtils.getOkResultOrFailure(httpResponse);
    }

    @Override
    public void close() {
        if (taxApiClient != null) {
            taxApiClient.close();
        }
    }
}

