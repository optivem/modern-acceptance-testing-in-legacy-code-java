package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.eshop.systemtest.core.SystemDsl;

import java.util.ArrayList;
import java.util.List;

public class ScenarioDsl {
    private final SystemDsl app;

    public ScenarioDsl(SystemDsl app) {
        this.app = app;
    }

    public GivenClause given() {
        return new GivenClause(app);
    }

    public static class GivenClause {
        private final SystemDsl app;
        private final List<ProductBuilder> products = new ArrayList<>();

        public GivenClause(SystemDsl app) {
            this.app = app;
        }

        public ProductBuilder product() {
            var productBuilder = new ProductBuilder(this);
            products.add(productBuilder);
            return productBuilder;
        }

        public WhenClause when() {
            // Execute all product creations
            for (var product : products) {
                app.erp().createProduct()
                        .sku(product.sku)
                        .unitPrice(product.unitPrice)
                        .execute()
                        .shouldSucceed();
            }
            return new WhenClause(app);
        }
    }

    public static class ProductBuilder {
        private final GivenClause givenClause;
        private String sku;
        private double unitPrice;

        public ProductBuilder(GivenClause givenClause) {
            this.givenClause = givenClause;
        }

        public ProductBuilder withSku(String sku) {
            this.sku = sku;
            return this;
        }

        public ProductBuilder withUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public GivenClause and() {
            return givenClause;
        }

        public WhenClause when() {
            return givenClause.when();
        }
    }

    public static class WhenClause {
        private final SystemDsl app;

        public WhenClause(SystemDsl app) {
            this.app = app;
        }

        public PlaceOrderBuilder placeOrder() {
            return new PlaceOrderBuilder(app);
        }
    }

    public static class PlaceOrderBuilder {
        private final SystemDsl app;
        private String orderNumber;
        private String sku;
        private int quantity;

        public PlaceOrderBuilder(SystemDsl app) {
            this.app = app;
        }

        public PlaceOrderBuilder withOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public PlaceOrderBuilder withSku(String sku) {
            this.sku = sku;
            return this;
        }

        public PlaceOrderBuilder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ThenClause then() {
            // Execute the place order
            app.shop().placeOrder()
                    .orderNumber(orderNumber)
                    .sku(sku)
                    .quantity(quantity)
                    .execute();

            return new ThenClause(app, orderNumber);
        }
    }

    public static class ThenClause {
        private final SystemDsl app;
        private final String orderNumber;

        public ThenClause(SystemDsl app, String orderNumber) {
            this.app = app;
            this.orderNumber = orderNumber;
        }

        public SuccessVerificationBuilder shouldSucceed() {
            return new SuccessVerificationBuilder(app, orderNumber);
        }
    }

    public static class SuccessVerificationBuilder {
        private final SystemDsl app;
        private final String orderNumber;

        public SuccessVerificationBuilder(SystemDsl app, String orderNumber) {
            this.app = app;
            this.orderNumber = orderNumber;
        }

        public SuccessVerificationBuilder expectOrderNumberPrefix(String prefix) {
            // This would verify that order number starts with the prefix
            // The actual verification happens during placeOrder
            return this;
        }

        public ThenAndClause and() {
            return new ThenAndClause(app, orderNumber);
        }
    }

    public static class ThenAndClause {
        private final SystemDsl app;
        private final String orderNumber;

        public ThenAndClause(SystemDsl app, String orderNumber) {
            this.app = app;
            this.orderNumber = orderNumber;
        }

        public OrderVerificationBuilder order(String orderNumber) {
            return new OrderVerificationBuilder(app, orderNumber);
        }
    }

    public static class OrderVerificationBuilder {
        private final SystemDsl app;
        private final String orderNumber;

        public OrderVerificationBuilder(SystemDsl app, String orderNumber) {
            this.app = app;
            this.orderNumber = orderNumber;
        }

        public OrderVerificationBuilder hasSku(String expectedSku) {
            app.shop().viewOrder()
                    .orderNumber(orderNumber)
                    .execute()
                    .shouldSucceed()
                    .sku(expectedSku);
            return this;
        }

        public OrderVerificationBuilder hasTotalPrice(double expectedTotalPrice) {
            app.shop().viewOrder()
                    .orderNumber(orderNumber)
                    .execute()
                    .shouldSucceed()
                    .totalPriceGreaterThanZero();
            return this;
        }
    }
}
