package com.optivem.eshop.systemtest.core.dsl.shop;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.CancelOrder;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.GoToShop;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.PlaceOrder;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.ViewOrder;
import com.optivem.lang.Closer;

import java.io.Closeable;

public class ShopDsl implements Closeable {
    private final ShopDriver driver;
    private final Context context;

    public ShopDsl(Context context) {
        this.driver = DriverFactory.createShopDriver();
        this.context = context;
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
