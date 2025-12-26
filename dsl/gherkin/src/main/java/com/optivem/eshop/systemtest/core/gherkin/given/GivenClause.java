package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import java.util.ArrayList;
import java.util.List;

public class GivenClause {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private final List<ProductBuilder> products;
    private final List<OrderBuilder> orders;
    private final List<ClockBuilder> clocks;
    private final List<TaxRateBuilder> taxRates;

    public GivenClause(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.clocks = new ArrayList<>();
        this.taxRates = new ArrayList<>();
    }

    public ProductBuilder product() {
        var productBuilder = new ProductBuilder(this);
        products.add(productBuilder);
        return productBuilder;
    }

    public OrderBuilder order() {
        var orderBuilder = new OrderBuilder(this);
        orders.add(orderBuilder);
        return orderBuilder;
    }

    public ClockBuilder clock() {
        var clockBuilder = new ClockBuilder(this);
        clocks.add(clockBuilder);
        return clockBuilder;
    }

    public TaxRateBuilder taxRate() {
        var taxRateBuilder = new TaxRateBuilder(this);
        taxRates.add(taxRateBuilder);
        return taxRateBuilder;
    }

    public WhenClause when() {
        for (var clock : clocks) {
            clock.execute(app);
        }

        // If we have orders but no products, add a default product
        if (!orders.isEmpty() && products.isEmpty()) {
            var defaultProduct = new ProductBuilder(this);
            products.add(defaultProduct);
        }

        // If we have orders but no tax rates, add a default tax rate
        if (!orders.isEmpty() && taxRates.isEmpty()) {
            var defaultTaxRate = new TaxRateBuilder(this);
            taxRates.add(defaultTaxRate);
        }

        for (var product : products) {
            product.execute(app);
        }
        
        for (var taxRate : taxRates) {
            taxRate.execute(app);
        }

        for (var order : orders) {
            order.execute(app);
        }

        return new WhenClause(app, scenario, !products.isEmpty(), !taxRates.isEmpty());
    }

    public boolean hasProducts() {
        return !products.isEmpty();
    }

    public boolean hasTaxRates() {
        return !taxRates.isEmpty();
    }
}
