package com.optivem.eshop.systemtest.core.shop.driver.ui;

import com.optivem.eshop.systemtest.core.shop.commons.Results;
import com.optivem.eshop.systemtest.core.shop.client.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.shop.driver.CouponDriver;
import com.optivem.eshop.systemtest.core.shop.driver.OrderDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopUiDriver implements ShopDriver {
    private static final Logger log = LoggerFactory.getLogger(ShopUiDriver.class);
    private final ShopUiClient client;
    private final PageNavigator pageNavigator;
    private final OrderDriver orderDriver;
    private final CouponDriver couponDriver;

    private HomePage homePage;

    public ShopUiDriver(String baseUrl) {
        long start = System.currentTimeMillis();
        this.client = new ShopUiClient(baseUrl);
        log.info("[PERF] ShopUiDriver - ShopUiClient creation took {}ms", System.currentTimeMillis() - start);
        this.pageNavigator = new PageNavigator();
        this.orderDriver = new ShopUiOrderDriver(this::getHomePage, pageNavigator);
        this.couponDriver = new ShopUiCouponDriver(this::getHomePage, pageNavigator);
    }

    @Override
    public Result<Void, SystemError> goToShop() {
        long start = System.currentTimeMillis();
        homePage = client.openHomePage();

        if (!client.isStatusOk() || !client.isPageLoaded()) {
            return Results.failure("Failed to load home page");
        }

        pageNavigator.setCurrentPage(PageNavigator.Page.HOME);
        log.info("[PERF] ShopUiDriver.goToShop() took {}ms", System.currentTimeMillis() - start);
        return Results.success();
    }

    @Override
    public OrderDriver orders() {
        return orderDriver;
    }

    @Override
    public CouponDriver coupons() {
        return couponDriver;
    }

    @Override
    public void close() {
        client.close();
    }

    private HomePage getHomePage() {
        if (homePage == null || !pageNavigator.isOnPage(PageNavigator.Page.HOME)) {
            homePage = client.openHomePage();
            pageNavigator.setCurrentPage(PageNavigator.Page.HOME);
        }
        return homePage;
    }
}

