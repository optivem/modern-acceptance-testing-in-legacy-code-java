package com.optivem.eshop.systemtest.core.shop.driver;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.error.SystemError;
import com.optivem.lang.Result;

public interface ShopDriver extends AutoCloseable {
    Result<Void, SystemError> goToShop();
    OrderDriver orders();
    CouponDriver coupons();
}
