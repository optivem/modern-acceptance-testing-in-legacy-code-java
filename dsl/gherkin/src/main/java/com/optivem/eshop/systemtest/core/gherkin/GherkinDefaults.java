package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.enums.OrderStatus;

/**
 * Default values for Gherkin test builders.
 * These defaults are used when test data is not explicitly specified.
 */
public final class GherkinDefaults {
    
    // Product defaults
    public static final String DEFAULT_SKU = "DEFAULT-SKU";
    public static final String DEFAULT_UNIT_PRICE = "20.00";
    
    // Order defaults
    public static final String DEFAULT_ORDER_NUMBER = "ORD-001";
    public static final String DEFAULT_QUANTITY = "1";
    public static final String DEFAULT_COUNTRY = "US";
    public static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.PLACED;

    // Clock defaults
    public static final String DEFAULT_TIME = "2025-12-24T10:00:00Z";
    
    // Tax defaults
    public static final String DEFAULT_TAX_RATE = "0.07";
    
    private GherkinDefaults() {
        // Prevent instantiation
    }
}
