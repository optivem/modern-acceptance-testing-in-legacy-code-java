package com.optivem.eshop.systemtest.v5.base;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.dsl.core.app.AppDsl;
import com.optivem.eshop.dsl.common.Closer;
import com.optivem.testing.extensions.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseAppDslTest extends BaseConfigurableTest {
    protected AppDsl app;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        app = createAppDsl(configuration);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
}

