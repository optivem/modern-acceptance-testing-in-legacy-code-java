package com.optivem.eshop.systemtest.core.shop.driver.ui.internal;

import com.optivem.eshop.systemtest.core.shop.client.ui.pages.CouponManagementPage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.core.shop.driver.internal.CouponDriver;
import com.optivem.commons.util.Result;

import java.util.function.Supplier;

import static com.optivem.eshop.systemtest.core.shop.commons.SystemResults.success;

public class ShopUiCouponDriver implements CouponDriver {
    private final Supplier<HomePage> homePageSupplier;
    private final PageNavigator pageNavigator;

    private CouponManagementPage couponManagementPage;

    public ShopUiCouponDriver(Supplier<HomePage> homePageSupplier, PageNavigator pageNavigator) {
        this.homePageSupplier = homePageSupplier;
        this.pageNavigator = pageNavigator;
    }

    @Override
    public Result<Void, SystemError> publishCoupon(PublishCouponRequest request) {
        ensureOnCouponManagementPage();

        couponManagementPage.inputCouponCode(request.getCode());
        couponManagementPage.inputDiscountRate(request.getDiscountRate());
        couponManagementPage.inputValidFrom(request.getValidFrom());
        couponManagementPage.inputValidTo(request.getValidTo());
        couponManagementPage.inputUsageLimit(request.getUsageLimit());
        couponManagementPage.clickPublishCoupon();

        return couponManagementPage.getResult().mapVoid();
    }

    @Override
    public Result<BrowseCouponsResponse, SystemError> browseCoupons(BrowseCouponsRequest request) {
        // Always navigate fresh to ensure we get the latest coupon data (e.g., updated used counts)
        navigateToCouponManagementPage();
        
        var coupons = couponManagementPage.readCoupons();
        
        var response = BrowseCouponsResponse.builder()
                .coupons(coupons)
                .build();
        
        return success(response);
    }

    private void ensureOnCouponManagementPage() {
        if (!pageNavigator.isOnPage(PageNavigator.Page.COUPON_MANAGEMENT)) {
            navigateToCouponManagementPage();
        }
    }

    private void navigateToCouponManagementPage() {
        var homePage = homePageSupplier.get();
        couponManagementPage = homePage.clickCouponManagement();
        pageNavigator.setCurrentPage(PageNavigator.Page.COUPON_MANAGEMENT);
    }
}

