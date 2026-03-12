package com.optivem.eshop.dsl.core.scenario.assume;

import com.optivem.eshop.dsl.core.app.AppDsl;
import com.optivem.eshop.dsl.port.assume.AssumeStage;
import com.optivem.eshop.dsl.port.assume.steps.AssumeRunning;

public class AssumeImpl implements AssumeStage {
    private final AppDsl app;

    public AssumeImpl(AppDsl app) {
        this.app = app;
    }

    @Override
    public AssumeRunning shop() {
        return () -> {
            app.shop().goToShop().execute().shouldSucceed();
            return this;
        };
    }

    @Override
    public AssumeRunning erp() {
        return () -> {
            app.erp().goToErp().execute().shouldSucceed();
            return this;
        };
    }

    @Override
    public AssumeRunning tax() {
        return () -> {
            app.tax().goToTax().execute().shouldSucceed();
            return this;
        };
    }

    @Override
    public AssumeRunning clock() {
        return () -> {
            app.clock().goToClock().execute().shouldSucceed();
            return this;
        };
    }
}


