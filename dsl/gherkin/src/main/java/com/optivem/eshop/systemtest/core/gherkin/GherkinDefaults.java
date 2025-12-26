package com.optivem.eshop.systemtest.core.gherkin;

import java.time.Instant;

/**
 * Default values for Gherkin test builders.
 * These defaults are used when test data is not explicitly specified.
 */
public final class GherkinDefaults {
    
    // Product defaults
    public static final String DEFAULT_SKU = "DEFAULT-SKU";
    public static final double DEFAULT_UNIT_PRICE = 20.00;
    
    // Order defaults
    public static final String DEFAULT_ORDER_NUMBER = "ORD-001";
    public static final int DEFAULT_QUANTITY = 1;
    public static final String DEFAULT_COUNTRY = "US";
    
    // Clock defaults
    public static final Instant DEFAULT_TIME = Instant.parse("2025-12-24T10:00:00Z");
    
    // Tax defaults
    public static final double DEFAULT_TAX_RATE = 0.07;
    
    private GherkinDefaults() {
        // Prevent instantiation
    }
}
