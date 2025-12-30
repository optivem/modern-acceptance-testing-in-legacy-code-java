package com.optivem.eshop.systemtest.core.shop.client.api.controllers;

import com.optivem.eshop.systemtest.core.shop.client.api.dtos.errors.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

public class CouponController {

    private static final String ENDPOINT = "/api/coupons";

    private final JsonHttpClient<ProblemDetailResponse> httpClient;

    public CouponController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, ProblemDetailResponse> publishCoupon(PublishCouponRequest request) {
        return httpClient.post(ENDPOINT, request);
    }

    public Result<BrowseCouponsResponse, ProblemDetailResponse> browseCoupons(BrowseCouponsRequest request) {
        return httpClient.get(ENDPOINT, BrowseCouponsResponse.class);
    }
}

