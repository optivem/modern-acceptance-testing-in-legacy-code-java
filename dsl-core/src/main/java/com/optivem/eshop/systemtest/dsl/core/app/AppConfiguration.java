package com.optivem.eshop.systemtest.dsl.core.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppConfiguration {
    private final String shopUiBaseUrl;
    private final String shopApiBaseUrl;
    private final String erpBaseUrl;
    private final String taxBaseUrl;
    private final String clockBaseUrl;
    private final ExternalSystemMode externalSystemMode;
}

