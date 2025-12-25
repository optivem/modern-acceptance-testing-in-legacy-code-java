package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import java.util.ArrayList;
import java.util.List;

public class GivenClause {
    private final SystemDsl app;
    private final List<ProductBuilder> products = new ArrayList<>();
    private final List<OrderBuilder> orders = new ArrayList<>();

    public GivenClause(SystemDsl app) {
        this.app = app;
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

    public EmptyGivenClause noProducts() {
        // No products to create, return clause that allows .when()
        return new EmptyGivenClause(app);
    }

    public WhenClause when() {
        // Execute all product creations
        for (var product : products) {
            app.erp().returnsProduct()
                    .sku(product.getSku())
                    .unitPrice(product.getUnitPrice())
                    .execute()
                    .shouldSucceed();
        }
        // Execute all order placements
        for (var order : orders) {
            app.shop().placeOrder()
                    .orderNumber(order.getOrderNumber())
                    .sku(order.getSku())
                    .execute()
                    .shouldSucceed();
        }
        return new WhenClause(app);
    }
}
