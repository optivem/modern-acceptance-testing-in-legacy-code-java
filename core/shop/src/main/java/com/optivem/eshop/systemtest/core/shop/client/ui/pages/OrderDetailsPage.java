package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.playwright.PageClient;

import java.math.BigDecimal;


public class OrderDetailsPage extends BasePage {
    private static final String ORDER_DETAILS_CONTAINER_SELECTOR = "[aria-label='Order Details']";
    private static final String ORDER_NUMBER_OUTPUT_SELECTOR = "[aria-label='Display Order Number']";
    private static final String SKU_OUTPUT_SELECTOR = "[aria-label='Display SKU']";
    private static final String COUNTRY_OUTPUT_SELECTOR = "[aria-label='Display Country']";
    private static final String QUANTITY_OUTPUT_SELECTOR = "[aria-label='Display Quantity']";
    private static final String UNIT_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Unit Price']";
    private static final String BASE_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Base Price']";
    private static final String SUBTOTAL_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Subtotal Price']";
    private static final String DISCOUNT_RATE_OUTPUT_SELECTOR = "[aria-label='Display Discount Rate']";
    private static final String DISCOUNT_AMOUNT_OUTPUT_SELECTOR = "[aria-label='Display Discount Amount']";
    private static final String TAX_RATE_OUTPUT_SELECTOR = "[aria-label='Display Tax Rate']";
    private static final String TAX_AMOUNT_OUTPUT_SELECTOR = "[aria-label='Display Tax Amount']";
    private static final String TOTAL_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Total Price']";
    private static final String STATUS_OUTPUT_SELECTOR = "[aria-label='Display Status']";
    private static final String APPLIED_COUPON_OUTPUT_SELECTOR = "[aria-label='Display Applied Coupon']";
    private static final String CANCEL_ORDER_OUTPUT_SELECTOR = "[aria-label='Cancel Order']";

    public OrderDetailsPage(PageClient pageClient) {
        super(pageClient);
    }

    public boolean isLoadedSuccessfully() {
        pageClient.waitForLoaded(ORDER_DETAILS_CONTAINER_SELECTOR);
        return pageClient.isLoadStateSuccess(ORDER_DETAILS_CONTAINER_SELECTOR);
    }

    public String getOrderNumber() {
        return pageClient.readTextContent(ORDER_NUMBER_OUTPUT_SELECTOR);
    }

    public String getSku() {
        return pageClient.readTextContent(SKU_OUTPUT_SELECTOR);
    }

    public String getCountry() {
        return pageClient.readTextContent(COUNTRY_OUTPUT_SELECTOR);
    }

    public int getQuantity() {
        var textContent = pageClient.readTextContent(QUANTITY_OUTPUT_SELECTOR);
        return Integer.parseInt(textContent);
    }

    public BigDecimal getUnitPrice() {
        return readTextMoney(UNIT_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getBasePrice() {
        return readTextMoney(BASE_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getDiscountRate() {
        return readTextPercentage(DISCOUNT_RATE_OUTPUT_SELECTOR);
    }

    public BigDecimal getDiscountAmount() {
        return readTextMoney(DISCOUNT_AMOUNT_OUTPUT_SELECTOR);
    }

    public BigDecimal getSubtotalPrice() {
        return readTextMoney(SUBTOTAL_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getTaxRate() {
        return readTextPercentage(TAX_RATE_OUTPUT_SELECTOR);
    }

    public BigDecimal getTaxAmount() {
        return readTextMoney(TAX_AMOUNT_OUTPUT_SELECTOR);
    }

    public BigDecimal getTotalPrice() {
        return readTextMoney(TOTAL_PRICE_OUTPUT_SELECTOR);
    }

    public OrderStatus getStatus() {
        var status = pageClient.readTextContent(STATUS_OUTPUT_SELECTOR);
        return OrderStatus.valueOf(status);
    }

    public String getAppliedCoupon() {
        var coupon = pageClient.readTextContent(APPLIED_COUPON_OUTPUT_SELECTOR);
        return "None".equals(coupon) ? null : coupon;
    }

    public void clickCancelOrder() {
        pageClient.click(CANCEL_ORDER_OUTPUT_SELECTOR);
        pageClient.waitForHidden(CANCEL_ORDER_OUTPUT_SELECTOR);
    }

    public boolean isCancelButtonHidden() {
        return pageClient.isHidden(CANCEL_ORDER_OUTPUT_SELECTOR);
    }

    private BigDecimal readTextMoney(String selector) {
        var textContent = pageClient.readTextContent(selector);
        var cleaned = textContent.replace("$", "").trim();
        return new BigDecimal(cleaned);
    }

    private BigDecimal readTextPercentage(String selector) {
        var textContent = pageClient.readTextContent(selector);
        var cleaned = textContent.replace("%", "").trim();
        var value = new BigDecimal(cleaned);
        return value.divide(BigDecimal.valueOf(100), 4, java.math.RoundingMode.HALF_UP);
    }
}

