package com.optivem.eshop.systemtest.dsl.core.system;

import com.optivem.eshop.systemtest.dsl.core.system.shared.ExternalSystemMode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SystemConfiguration {
    private final String shopUiBaseUrl;
    private final String shopApiBaseUrl;
    private final String erpBaseUrl;
    private final String taxBaseUrl;
    private final String clockBaseUrl;
    private final ExternalSystemMode externalSystemMode;
}

