package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import java.util.ArrayList;
import java.util.List;

public class GivenClause {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private final List<ProductBuilder> products = new ArrayList<>();
    private final List<OrderBuilder> orders = new ArrayList<>();
    private final List<OrderBuilder> cancelledOrders = new ArrayList<>();
    private final List<ClockBuilder> clocks = new ArrayList<>();
    private final List<TaxRateBuilder> taxRates = new ArrayList<>();

    public GivenClause(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;
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

    public OrderBuilder cancelledOrder() {
        var orderBuilder = new OrderBuilder(this);
        cancelledOrders.add(orderBuilder);
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

    public EmptyGivenClause noProducts() {
        // No products to create, return clause that allows .when()
        return new EmptyGivenClause(app, scenario);
    }

    public WhenClause when() {
        // Collect all unique countries from orders (including cancelled orders)
        var allOrders = new ArrayList<OrderBuilder>();
        allOrders.addAll(orders);
        allOrders.addAll(cancelledOrders);

        // Execute all clock setups
        // If there are orders but no clock configured, set up a default clock
        if (!allOrders.isEmpty() && clocks.isEmpty()) {
            app.clock().returnsTime()
                    .time(java.time.Instant.parse("2024-01-01T12:00:00Z"))
                    .execute()
                    .shouldSucceed();
        }
        
        for (var clock : clocks) {
            app.clock().returnsTime()
                    .time(clock.getTime())
                    .execute()
                    .shouldSucceed();
        }

        // Execute all product creations
        for (var product : products) {
            app.erp().returnsProduct()
                    .sku(product.getSku())
                    .unitPrice(product.getUnitPrice())
                    .execute()
                    .shouldSucceed();
        }

        var countriesInOrders = allOrders.stream()
                .map(OrderBuilder::getCountry)
                .distinct()
                .toList();

        // Execute all tax rate setups
        for (var taxRate : taxRates) {
            app.tax().returnsTaxRate()
                    .country(taxRate.getCountry())
                    .taxRate(taxRate.getTaxRate())
                    .execute()
                    .shouldSucceed();
        }

        // Ensure tax rates are set up for all countries used in orders
        var configuredTaxCountries = taxRates.stream()
                .map(TaxRateBuilder::getCountry)
                .toList();

        for (var country : countriesInOrders) {
            if (!configuredTaxCountries.contains(country)) {
                // Set up default tax rate (0.0) for countries without explicit tax configuration
                app.tax().returnsTaxRate()
                        .country(country)
                        .taxRate(0.0)
                        .execute()
                        .shouldSucceed();
            }
        }

        // Execute all order placements
        for (var order : orders) {
            app.shop().placeOrder()
                    .orderNumber(order.getOrderNumber())
                    .sku(order.getSku())
                    .quantity(order.getQuantity())
                    .country(order.getCountry())
                    .execute()
                    .shouldSucceed();
        }

        // Execute all cancelled order placements and cancellations
        for (var order : cancelledOrders) {
            app.shop().placeOrder()
                    .orderNumber(order.getOrderNumber())
                    .sku(order.getSku())
                    .quantity(order.getQuantity())
                    .country(order.getCountry())
                    .execute()
                    .shouldSucceed();

            app.shop().cancelOrder()
                    .orderNumber(order.getOrderNumber())
                    .execute()
                    .shouldSucceed();
        }

        return new WhenClause(app, scenario);
    }
}
