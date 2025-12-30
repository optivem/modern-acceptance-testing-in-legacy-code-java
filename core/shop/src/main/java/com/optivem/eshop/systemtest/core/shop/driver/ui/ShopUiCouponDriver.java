package com.optivem.eshop.systemtest.core.shop.driver.ui;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.core.shop.driver.CouponDriver;
import com.optivem.lang.Result;

public class ShopUiCouponDriver implements CouponDriver {
    @Override
    public Result<Void, SystemError> publishCoupon(PublishCouponRequest request) {
        throw new UnsupportedOperationException("UI testing for publishCoupon not implemented yet. Please use API channel for coupon testing.");
    }

    @Override
    public Result<BrowseCouponsResponse, SystemError> browseCoupons(BrowseCouponsRequest request) {
        throw new UnsupportedOperationException("UI testing for browseCoupons not implemented yet. Please use API channel for coupon testing.");
    }
}

