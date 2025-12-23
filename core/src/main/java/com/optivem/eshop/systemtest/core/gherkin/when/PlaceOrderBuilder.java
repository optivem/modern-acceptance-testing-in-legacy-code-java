package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public class PlaceOrderBuilder {
    private static final String DEFAULT_SKU = "DEFAULT-SKU";
    private static final int DEFAULT_QUANTITY = 1;
    private static final String DEFAULT_COUNTRY = "US";

    private final SystemDsl app;
    private String orderNumber;
    private String sku = DEFAULT_SKU;
    private String quantityString;
    private Integer quantityInt = DEFAULT_QUANTITY;
    private String country = DEFAULT_COUNTRY;
    private boolean skuExplicitlySet = false;
    private boolean quantityExplicitlySet = false;
    private boolean countryExplicitlySet = false;

    public PlaceOrderBuilder(SystemDsl app) {
        this.app = app;
    }

    public PlaceOrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public PlaceOrderBuilder withSku(String sku) {
        this.sku = sku;
        this.skuExplicitlySet = true;
        return this;
    }

    public PlaceOrderBuilder withQuantity(int quantity) {
        this.quantityInt = quantity;
        this.quantityString = null;
        this.quantityExplicitlySet = true;
        return this;
    }

    /**
     * Accepts a String quantity for validation tests (empty, non-integer values).
     * For parameterized tests with valid integer strings, use this method.
     */
    public PlaceOrderBuilder withQuantity(String quantity) {
        this.quantityString = quantity;
        this.quantityInt = null;
        this.quantityExplicitlySet = true;
        return this;
    }

    public PlaceOrderBuilder withCountry(String country) {
        this.country = country;
        this.countryExplicitlySet = true;
        return this;
    }

    public ThenClause then() {
        // Execute the place order
        var placeOrder = app.shop().placeOrder()
                .orderNumber(orderNumber);

        // Apply SKU - use explicit value if set, otherwise use default
        if (skuExplicitlySet) {
            placeOrder.sku(sku);
        } else {
            placeOrder.sku(DEFAULT_SKU);
        }

        // Apply quantity
        if (quantityExplicitlySet) {
            if (quantityString != null) {
                placeOrder.quantity(quantityString);
            } else if (quantityInt != null) {
                placeOrder.quantity(quantityInt);
            } else {
                // null was explicitly passed as String
                placeOrder.quantity((String) null);
            }
        } else {
            placeOrder.quantity(DEFAULT_QUANTITY);
        }

        // Apply country
        if (countryExplicitlySet) {
            placeOrder.country(country);
        } else {
            placeOrder.country(DEFAULT_COUNTRY);
        }

        var result = placeOrder.execute();

        return new ThenClause(app, orderNumber, result);
    }
}
