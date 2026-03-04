package com.optivem.eshop.systemtest.dsl.core.scenario.shop.then;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps.ThenClockImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps.ThenProductImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps.ThenCountryImpl;
import com.optivem.eshop.systemtest.dsl.port.shop.then.Then;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenClock;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenCountry;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenProduct;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenSuccess;

public class ThenImpl implements Then {
    protected final AppDsl app;

    public ThenImpl(AppDsl app) {
        this.app = app;
    }

    @Override
    public ThenClock clock() {
        var verification = app.clock().getTime().execute().shouldSucceed();
        return new ThenClockImpl(verification);
    }

    @Override
    public ThenProduct product(String skuAlias) {
        var verification = app.erp().getProduct().sku(skuAlias).execute().shouldSucceed();
        return new ThenProductImpl(verification);
    }

    @Override
    public ThenCountry country(String countryAlias) {
        var verification = app.tax().getTaxRate().country(countryAlias).execute().shouldSucceed();
        return new ThenCountryImpl(verification);
    }

    @Override
    public ThenSuccess shouldSucceed() {
        throw new IllegalStateException("Cannot verify success: no operation was executed");
    }

    @Override
    public ThenFailure shouldFail() {
        throw new IllegalStateException("Cannot verify failure: no operation was executed");
    }
}
