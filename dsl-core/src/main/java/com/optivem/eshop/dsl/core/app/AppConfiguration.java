package com.optivem.eshop.dsl.core.app;

import com.optivem.eshop.dsl.port.ExternalSystemMode;
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


