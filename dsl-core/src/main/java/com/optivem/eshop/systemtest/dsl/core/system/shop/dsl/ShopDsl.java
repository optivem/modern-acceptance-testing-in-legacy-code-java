package com.optivem.eshop.systemtest.dsl.core.system.shop.dsl;

import com.optivem.eshop.systemtest.driver.api.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.GoToShop;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.BrowseCoupons;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.CancelOrder;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.PlaceOrder;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.PublishCoupon;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.ViewOrder;
import com.optivem.commons.util.Closer;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

import java.io.Closeable;

public class ShopDsl implements Closeable {
    private final ShopDriver driver;
    private final UseCaseContext context;

    public ShopDsl(ShopDriver driver, UseCaseContext context) {
        this.driver = driver;
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

    public PublishCoupon publishCoupon() {
        return new PublishCoupon(driver, context);
    }

    public BrowseCoupons browseCoupons() {
        return new BrowseCoupons(driver, context);
    }
}
