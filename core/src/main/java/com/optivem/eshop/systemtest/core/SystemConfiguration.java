package com.optivem.eshop.systemtest.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SystemConfiguration {
    private final String shopUiBaseUrl;
    private final String shopApiBaseUrl;
    private final String erpBaseUrl;
    private final String taxBaseUrl;
}
