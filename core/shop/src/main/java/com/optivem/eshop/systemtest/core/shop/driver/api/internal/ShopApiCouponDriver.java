
package com.optivem.eshop.systemtest.core.shop.driver.api.internal;

import com.optivem.eshop.systemtest.core.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.core.shop.driver.internal.CouponDriver;
import com.optivem.commons.util.Result;

public class ShopApiCouponDriver implements CouponDriver {
    private final ShopApiClient apiClient;

    public ShopApiCouponDriver(ShopApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Result<Void, SystemError> publishCoupon(PublishCouponRequest request) {
        return apiClient.coupons().publishCoupon(request).mapError(SystemError::from);
    }

    @Override
    public Result<BrowseCouponsResponse, SystemError> browseCoupons() {
        return apiClient.coupons().browseCoupons().mapError(SystemError::from);
    }
}

