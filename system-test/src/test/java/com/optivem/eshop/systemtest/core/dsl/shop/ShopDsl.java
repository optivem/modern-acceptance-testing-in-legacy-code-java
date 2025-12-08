package com.optivem.eshop.systemtest.core.dsl.shop;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.confirm.ConfirmShopOpened;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.execute.CancelOrder;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.execute.GoToShop;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.execute.PlaceOrder;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.execute.ViewOrder;

public class ShopDsl {
    private final ShopDriver driver;
    private final DslContext context;

    public ShopDsl(ShopDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public void goToShop() {
        new GoToShop(driver, context).execute();
    }

    public void confirmShopOpened() {
        new ConfirmShopOpened(driver, context).execute();
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
