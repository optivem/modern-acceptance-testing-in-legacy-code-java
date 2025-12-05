package com.optivem.eshop.systemtest.core.dsl.system;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.confirm.ConfirmShopOpened;
import com.optivem.eshop.systemtest.core.dsl.system.commands.execute.CancelOrder;
import com.optivem.eshop.systemtest.core.dsl.system.commands.execute.GoToShop;
import com.optivem.eshop.systemtest.core.dsl.system.commands.execute.PlaceOrder;
import com.optivem.eshop.systemtest.core.dsl.system.commands.execute.ViewOrder;
import com.optivem.lang.Closer;

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
