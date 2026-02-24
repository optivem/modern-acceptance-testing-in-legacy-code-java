package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.port.GivenPort;
import com.optivem.eshop.systemtest.core.gherkin.when.When;

import java.util.ArrayList;
import java.util.List;

public class Given implements GivenPort {
    private final SystemDsl app;
    private final List<GivenProduct> products;
    private final List<GivenOrder> orders;
    private GivenClock clock;
    private final List<GivenCountry> countries;
    private final List<GivenCoupon> coupons;

    public Given(SystemDsl app) {
        this.app = app;
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.clock = new GivenClock(this);
        this.countries = new ArrayList<>();
        this.coupons = new ArrayList<>();
    }

    public GivenProduct product() {
        var product = new GivenProduct(this);
        products.add(product);
        return product;
    }

    public GivenOrder order() {
        var order = new GivenOrder(this);
        orders.add(order);
        return order;
    }

    public GivenClock clock() {
        clock = new GivenClock(this);
        return clock;
    }

    public GivenCountry country() {
        var country = new GivenCountry(this);
        countries.add(country);
        return country;
    }

    public GivenCoupon coupon() {
        var coupon = new GivenCoupon(this);
        coupons.add(coupon);
        return coupon;
    }

    public When when() {
        setupClock();
        setupErp();
        setupTax();
        setupShop();

        return new When(app, !products.isEmpty(), !countries.isEmpty());
    }

    private void setupClock() {
        clock.execute(app);
    }

    private void setupErp() {
        if (!orders.isEmpty() && products.isEmpty()) {
            var defaultProduct = new GivenProduct(this);
            products.add(defaultProduct);
        }

        for (var product : products) {
            product.execute(app);
        }
    }

    private void setupTax() {
        if (!orders.isEmpty() && countries.isEmpty()) {
            var defaultCountry = new GivenCountry(this);
            countries.add(defaultCountry);
        }

        for (var country : countries) {
            country.execute(app);
        }
    }

    private void setupShop() {
        setupCoupons();
        setupOrders();
    }

    private void setupCoupons() {
        if(!orders.isEmpty() && coupons.isEmpty()) {
            var defaultCoupon = new GivenCoupon(this);
            coupons.add(defaultCoupon);
        }

        for (var coupon : coupons) {
            coupon.execute(app);
        }
    }

    private void setupOrders() {
        for (var order : orders) {
            order.execute(app);
        }
    }
}