package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.dsl.Dsl;
import com.optivem.eshop.systemtest.dsl.DslConfiguration;

public class DslFactory {
    public static Dsl create() {
        var configuration = DslConfigurationReader.readConfiguration();
        return new Dsl(configuration);
    }
}
