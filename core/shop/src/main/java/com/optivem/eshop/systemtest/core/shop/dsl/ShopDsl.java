package com.optivem.eshop.systemtest.core.shop.dsl;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.driver.api.ShopApiDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ui.ShopUiDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.BrowseCoupons;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.CancelOrder;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.GoToShop;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.PlaceOrder;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.PublishCoupon;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.ViewOrder;
import com.optivem.lang.Closer;
import com.optivem.test.channels.ChannelContext;
import com.optivem.test.dsl.UseCaseContext;

import java.io.Closeable;

public class ShopDsl implements Closeable {
    private final ShopDriver driver;
    private final UseCaseContext context;

    public ShopDsl(String uiBaseUrl, String apiBaseUrl, UseCaseContext context) {
        this.driver = createDriver(uiBaseUrl, apiBaseUrl);
        this.context = context;
    }

    private static ShopDriver createDriver(String uiBaseUrl, String apiBaseUrl) {
        var channel = ChannelContext.get();
        if (ChannelType.UI.equals(channel)) {
            return new ShopUiDriver(uiBaseUrl);
        } else if (ChannelType.API.equals(channel)) {
            return new ShopApiDriver(apiBaseUrl);
        } else {
            throw new IllegalStateException("Unknown channel: " + channel);
        }
    }

    @Override
    public void close() {
        Closer.close(driver);
    }

    public GoToShop goToShop() {
        return new GoToShop(driver, context);
    }

    public PlaceOrder placeOrder() {
        return new PlaceOrder(driver, context);
    }

    public CancelOrder cancelOrder() {
        return new CancelOrder(driver, context);
    }

    public ViewOrder viewOrder() {
        return new ViewOrder(driver, context);
    }

    public PublishCoupon publishCoupon() {
        return new PublishCoupon(driver, context);
    }

    public BrowseCoupons browseCoupons() {
        return new BrowseCoupons(driver, context);
    }
}
