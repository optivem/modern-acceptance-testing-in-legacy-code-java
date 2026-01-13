package com.optivem.eshop.systemtest.core.shop.driver;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.util.Result;

public interface ShopDriver extends AutoCloseable {
    Result<Void, SystemError> goToShop();
    OrderDriver orders();
    CouponDriver coupons();
}
