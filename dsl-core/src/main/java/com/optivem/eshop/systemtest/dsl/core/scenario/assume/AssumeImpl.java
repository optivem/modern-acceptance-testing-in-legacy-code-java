package com.optivem.eshop.systemtest.dsl.core.scenario.assume;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.port.assume.AssumeStage;
import com.optivem.eshop.systemtest.dsl.port.assume.steps.Should;

public class AssumeImpl implements AssumeStage {
    private final AppDsl app;

    public AssumeImpl(AppDsl app) {
        this.app = app;
    }

    @Override
    public Should shop() {
        return () -> {
            app.shop().goToShop().execute().shouldSucceed();
            return this;
        };
    }

    @Override
    public Should erp() {
        return () -> {
            app.erp().goToErp().execute().shouldSucceed();
            return this;
        };
    }

    @Override
    public Should tax() {
        return () -> {
            app.tax().goToTax().execute().shouldSucceed();
            return this;
        };
    }

    @Override
    public Should clock() {
        return () -> {
            app.clock().goToClock().execute().shouldSucceed();
            return this;
        };
    }
}


