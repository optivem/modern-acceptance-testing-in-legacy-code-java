package com.optivem.eshop.systemtest.core.dsl.system;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.execute.CancelOrderCommand;
import com.optivem.eshop.systemtest.core.dsl.system.commands.execute.GoToShopCommand;
import com.optivem.eshop.systemtest.core.dsl.system.commands.execute.PlaceOrderCommand;
import com.optivem.eshop.systemtest.core.dsl.system.commands.execute.ViewOrderCommand;

public class ShopDsl {
    private final ShopDriver driver;
    private final DslContext context;

    public ShopDsl(ShopDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public GoToShopCommand goToShop() {
        return new GoToShopCommand(driver, context);
    }

    public PlaceOrderCommand placeOrder() {
        return new PlaceOrderCommand(driver, context);
    }

    public CancelOrderCommand cancelOrder() {
        return new CancelOrderCommand(driver, context);
    }

    public ViewOrderCommand viewOrder() {
        return new ViewOrderCommand(driver, context);
    }

}
