package com.optivem.eshop.systemtest.dsl.core;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.BackgroundDslImpl;
import com.optivem.eshop.systemtest.dsl.core.ScenarioDslImpl;
import com.optivem.eshop.systemtest.dsl.port.FeatureDsl;
import com.optivem.eshop.systemtest.dsl.port.BackgroundDsl;

public class FeatureDslImpl implements FeatureDsl {
    private final AppDsl app;

    public FeatureDslImpl(AppDsl app) {
        this.app = app;
    }

    @Override
    public BackgroundDsl background() {
        return new BackgroundDslImpl(app);
    }

    @Override
    public ScenarioDslImpl scenario() {
        return new ScenarioDslImpl(app);
    }
}
