package com.optivem.eshop.systemtest.base.v5;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.common.Closer;
import com.optivem.testing.extensions.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseSystemDslTest extends BaseConfigurableTest {
    protected SystemDsl app;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        app = createSystemDsl(configuration);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
}

