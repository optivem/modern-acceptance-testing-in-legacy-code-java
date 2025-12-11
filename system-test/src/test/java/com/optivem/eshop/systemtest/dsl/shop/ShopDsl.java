package com.optivem.eshop.systemtest.dsl.shop;

import com.optivem.eshop.systemtest.dsl.DslConfiguration;
import com.optivem.eshop.systemtest.dsl.shop.drivers.ShopDriver;
import com.optivem.eshop.systemtest.dsl.shop.drivers.api.ShopApiDriver;
import com.optivem.eshop.systemtest.dsl.shop.drivers.ui.ShopUiDriver;
import com.optivem.testing.channels.ChannelContext;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.dsl.shop.commands.CancelOrder;
import com.optivem.eshop.systemtest.dsl.shop.commands.GoToShop;
import com.optivem.eshop.systemtest.dsl.shop.commands.PlaceOrder;
import com.optivem.eshop.systemtest.dsl.shop.commands.ViewOrder;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class ShopDsl implements Closeable {
    private final ShopDriver driver;
    private final Context context;

    public ShopDsl(Context context, DslConfiguration configuration) {
        this.driver = createDriver(configuration);
        this.context = context;
    }

    private static ShopDriver createDriver(DslConfiguration configuration) {
        var channel = ChannelContext.get();
        if (ChannelType.UI.equals(channel)) {
            return new ShopUiDriver(configuration.getShopUiBaseUrl());
        } else if (ChannelType.API.equals(channel)) {
            return new ShopApiDriver(configuration.getShopApiBaseUrl());
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
}
