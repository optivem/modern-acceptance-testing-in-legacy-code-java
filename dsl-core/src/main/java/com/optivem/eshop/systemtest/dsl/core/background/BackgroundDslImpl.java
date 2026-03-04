package com.optivem.eshop.systemtest.dsl.core.background;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.port.background.BackgroundDsl;

public class BackgroundDslImpl implements BackgroundDsl {
    private final AppDsl app;

    public BackgroundDslImpl(AppDsl app) {
        this.app = app;
    }

    @Override
    public BackgroundDsl shopRunning() {
        app.shop().goToShop().execute().shouldSucceed();
        return this;
    }

    @Override
    public BackgroundDsl erpRunning() {
        app.erp().goToErp().execute().shouldSucceed();
        return this;
    }

    @Override
    public BackgroundDsl taxRunning() {
        app.tax().goToTax().execute().shouldSucceed();
        return this;
    }

    @Override
    public BackgroundDsl clockRunning() {
        app.clock().goToClock().execute().shouldSucceed();
        return this;
    }
}
