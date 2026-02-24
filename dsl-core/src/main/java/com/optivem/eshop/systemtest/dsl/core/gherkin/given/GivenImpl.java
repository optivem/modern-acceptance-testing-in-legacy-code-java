package com.optivem.eshop.systemtest.dsl.core.gherkin.given;

import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.steps.GivenClockImpl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.steps.GivenCountryImpl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.steps.GivenCouponImpl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.steps.GivenOrderImpl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.steps.GivenProductImpl;
import com.optivem.eshop.systemtest.dsl.api.given.GivenPort;
import com.optivem.eshop.systemtest.dsl.core.gherkin.when.WhenImpl;

import java.util.ArrayList;
import java.util.List;

public class GivenImpl implements GivenPort {
    private final SystemDsl app;
    private final List<GivenProductImpl> products;
    private final List<GivenOrderImpl> orders;
    private GivenClockImpl clock;
    private final List<GivenCountryImpl> countries;
    private final List<GivenCouponImpl> coupons;

    public GivenImpl(SystemDsl app) {
        this.app = app;
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.clock = new GivenClockImpl(this);
        this.countries = new ArrayList<>();
        this.coupons = new ArrayList<>();
    }

    public GivenProductImpl product() {
        var product = new GivenProductImpl(this);
        products.add(product);
        return product;
    }

    public GivenOrderImpl order() {
        var order = new GivenOrderImpl(this);
        orders.add(order);
        return order;
    }

    public GivenClockImpl clock() {
        clock = new GivenClockImpl(this);
        return clock;
    }

    public GivenCountryImpl country() {
        var country = new GivenCountryImpl(this);
        countries.add(country);
        return country;
    }

    public GivenCouponImpl coupon() {
        var coupon = new GivenCouponImpl(this);
        coupons.add(coupon);
        return coupon;
    }

    public WhenImpl when() {
        setupClock();
        setupErp();
        setupTax();
        setupShop();

        return new WhenImpl(app, !products.isEmpty(), !countries.isEmpty());
    }

    private void setupClock() {
        clock.execute(app);
    }

    private void setupErp() {
        if (!orders.isEmpty() && products.isEmpty()) {
            var defaultProduct = new GivenProductImpl(this);
            products.add(defaultProduct);
        }

        for (var product : products) {
            product.execute(app);
        }
    }

    private void setupTax() {
        if (!orders.isEmpty() && countries.isEmpty()) {
            var defaultCountry = new GivenCountryImpl(this);
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
            var defaultCoupon = new GivenCouponImpl(this);
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