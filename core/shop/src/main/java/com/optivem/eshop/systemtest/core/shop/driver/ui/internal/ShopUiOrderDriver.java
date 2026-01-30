package com.optivem.eshop.systemtest.core.shop.driver.ui.internal;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.ViewOrderResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.OrderDetailsPage;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.OrderHistoryPage;
import com.optivem.eshop.systemtest.core.shop.driver.internal.OrderDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.util.Result;

import java.util.Objects;
import java.util.function.Supplier;

import static com.optivem.eshop.systemtest.core.shop.commons.SystemResults.failure;
import static com.optivem.eshop.systemtest.core.shop.commons.SystemResults.success;

public class ShopUiOrderDriver implements OrderDriver {
    private final Supplier<HomePage> homePageSupplier;
    private final PageNavigator pageNavigator;

    private NewOrderPage newOrderPage;
    private OrderHistoryPage orderHistoryPage;
    private OrderDetailsPage orderDetailsPage;

    public ShopUiOrderDriver(Supplier<HomePage> homePageSupplier, PageNavigator pageNavigator) {
        this.homePageSupplier = homePageSupplier;
        this.pageNavigator = pageNavigator;
    }

    @Override
    public Result<PlaceOrderResponse, SystemError> placeOrder(PlaceOrderRequest request) {
        var sku = request.getSku();
        var quantity = request.getQuantity();
        var country = request.getCountry();
        var couponCode = request.getCouponCode();

        ensureOnNewOrderPage();
        newOrderPage.inputSku(sku);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.inputCouponCode(couponCode);
        newOrderPage.clickPlaceOrder();

        var result = newOrderPage.getResult();

        if (result.isFailure()) {
            return failure(result.getError());
        }

        var orderNumberValue = NewOrderPage.getOrderNumber(result.getValue());

        var response = PlaceOrderResponse.builder().orderNumber(orderNumberValue).build();
        return Result.success(response);
    }

    @Override
    public Result<ViewOrderResponse, SystemError> viewOrder(String orderNumber) {
        var result = ensureOnOrderDetailsPage(orderNumber);
        if (result.isFailure()) {
            return failure(result.getError());
        }

        var isSuccess = orderDetailsPage.isLoadedSuccessfully();

        if (!isSuccess) {
            return Result.failure(result.getError());
        }

        var displayOrderNumber = orderDetailsPage.getOrderNumber();
        var orderTimestamp = orderDetailsPage.getOrderTimestamp();
        var sku = orderDetailsPage.getSku();
        var quantity = orderDetailsPage.getQuantity();
        var country = orderDetailsPage.getCountry();
        var unitPrice = orderDetailsPage.getUnitPrice();
        var basePrice = orderDetailsPage.getBasePrice();
        var discountRate = orderDetailsPage.getDiscountRate();
        var discountAmount = orderDetailsPage.getDiscountAmount();
        var subtotalPrice = orderDetailsPage.getSubtotalPrice();
        var taxRate = orderDetailsPage.getTaxRate();
        var taxAmount = orderDetailsPage.getTaxAmount();
        var totalPrice = orderDetailsPage.getTotalPrice();
        var status = orderDetailsPage.getStatus();
        var appliedCoupon = orderDetailsPage.getAppliedCoupon();

        var response = ViewOrderResponse.builder()
                .orderNumber(displayOrderNumber)
                .orderTimestamp(orderTimestamp)
                .sku(sku)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .basePrice(basePrice)
                .discountRate(discountRate)
                .discountAmount(discountAmount)
                .subtotalPrice(subtotalPrice)
                .taxRate(taxRate)
                .taxAmount(taxAmount)
                .totalPrice(totalPrice)
                .country(country)
                .status(status)
                .appliedCouponCode(appliedCoupon)
                .build();

        return success(response);
    }

    @Override
    public Result<Void, SystemError> cancelOrder(String orderNumber) {
        var viewResult = viewOrder(orderNumber);

        if(viewResult.isFailure()) {
            return viewResult.mapVoid();
        }

        orderDetailsPage.clickCancelOrder();

        var result = orderDetailsPage.getResult();

        if(result.isFailure()) {
            return result.mapVoid();
        }

        var cancellationMessage = result.getValue();
        if (!Objects.equals(cancellationMessage, "Order cancelled successfully!")) {
            return failure("Did not receive expected cancellation success message");
        }

        var displayStatusAfterCancel = orderDetailsPage.getStatus();
        if (!Objects.equals(displayStatusAfterCancel, OrderStatus.CANCELLED)) {
            return failure("Order status not updated to CANCELLED");
        }

        if (!orderDetailsPage.isCancelButtonHidden()) {
            return failure("Cancel button still visible");
        }

        return success();
    }

    private void ensureOnNewOrderPage() {
        if (!pageNavigator.isOnPage(PageNavigator.Page.NEW_ORDER)) {
            var homePage = homePageSupplier.get();
            newOrderPage = homePage.clickNewOrder();
            pageNavigator.setCurrentPage(PageNavigator.Page.NEW_ORDER);
        }
    }

    private void ensureOnOrderHistoryPage() {
        if (!pageNavigator.isOnPage(PageNavigator.Page.ORDER_HISTORY)) {
            var homePage = homePageSupplier.get();
            orderHistoryPage = homePage.clickOrderHistory();
            pageNavigator.setCurrentPage(PageNavigator.Page.ORDER_HISTORY);
        }
    }

    private Result<Void, SystemError> ensureOnOrderDetailsPage(String orderNumber) {
        ensureOnOrderHistoryPage();
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();

        var isOrderListed = orderHistoryPage.isOrderListed(orderNumber);
        if (!isOrderListed) {
            return failure("Order " + orderNumber + " does not exist.");
        }

        orderDetailsPage = orderHistoryPage.clickViewOrderDetails(orderNumber);
        pageNavigator.setCurrentPage(PageNavigator.Page.ORDER_DETAILS);

        return success();
    }
}

