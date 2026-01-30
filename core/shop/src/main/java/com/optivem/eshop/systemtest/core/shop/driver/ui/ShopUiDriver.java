package com.optivem.eshop.systemtest.core.shop.driver.ui;

import com.optivem.eshop.systemtest.core.shop.client.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.shop.driver.internal.CouponDriver;
import com.optivem.eshop.systemtest.core.shop.driver.internal.OrderDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.util.Result;
import com.optivem.eshop.systemtest.core.shop.driver.ui.internal.PageNavigator;
import com.optivem.eshop.systemtest.core.shop.driver.ui.internal.ShopUiCouponDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ui.internal.ShopUiOrderDriver;

import static com.optivem.eshop.systemtest.core.shop.commons.SystemResults.failure;
import static com.optivem.eshop.systemtest.core.shop.commons.SystemResults.success;

public class ShopUiDriver implements ShopDriver {
    private final ShopUiClient client;
    private final PageNavigator pageNavigator;
    private final OrderDriver orderDriver;
    private final CouponDriver couponDriver;

    private HomePage homePage;

    public ShopUiDriver(String baseUrl) {
        this.client = new ShopUiClient(baseUrl);
        this.pageNavigator = new PageNavigator();
        this.orderDriver = new ShopUiOrderDriver(this::getHomePage, pageNavigator);
        this.couponDriver = new ShopUiCouponDriver(this::getHomePage, pageNavigator);
    }

    @Override
    public void close() {
        client.close();
    }

    @Override
    public Result<Void, SystemError> goToShop() {
        homePage = client.openHomePage();

        if (!client.isStatusOk() || !client.isPageLoaded()) {
            return failure("Failed to load home page");
        }

        pageNavigator.setCurrentPage(PageNavigator.Page.HOME);
        return success();
    }

    @Override
    public OrderDriver orders() {
        return orderDriver;
    }

    @Override
    public CouponDriver coupons() {
        return couponDriver;
    }

    private HomePage getHomePage() {
        if (homePage == null || !pageNavigator.isOnPage(PageNavigator.Page.HOME)) {
            homePage = client.openHomePage();
            pageNavigator.setCurrentPage(PageNavigator.Page.HOME);
        }
        return homePage;
    }
}

