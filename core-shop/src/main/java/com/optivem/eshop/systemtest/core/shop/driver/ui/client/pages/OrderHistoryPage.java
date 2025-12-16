package com.optivem.eshop.systemtest.core.shop.driver.ui.client.pages;

import com.optivem.playwright.PageClient;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.enums.OrderStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderHistoryPage extends BasePage {

    private static final String ORDER_NUMBER_INPUT_SELECTOR = "[aria-label='Order Number']";
    private static final String SEARCH_BUTTON_SELECTOR = "[aria-label='Search']";
    private static final String ORDER_DETAILS_CONTAINER_SELECTOR = "#orderDetails";
    private static final String ORDER_NUMBER_OUTPUT_SELECTOR = "[aria-label='Display Order Number']";
    private static final String SKU_OUTPUT_SELECTOR = "[aria-label='Display SKU']";
    private static final String COUNTRY_OUTPUT_SELECTOR = "[aria-label='Display Country']";
    private static final String QUANTITY_OUTPUT_SELECTOR = "[aria-label='Display Quantity']";
    private static final String UNIT_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Unit Price']";
    private static final String ORIGINAL_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Original Price']";
    private static final String DISCOUNT_RATE_OUTPUT_SELECTOR = "[aria-label='Display Discount Rate']";
    private static final String DISCOUNT_AMOUNT_OUTPUT_SELECTOR = "[aria-label='Display Discount Amount']";
    private static final String SUBTOTAL_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Subtotal Price']";
    private static final String TAX_RATE_OUTPUT_SELECTOR = "[aria-label='Display Tax Rate']";
    private static final String TAX_AMOUNT_OUTPUT_SELECTOR = "[aria-label='Display Tax Amount']";
    private static final String TOTAL_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Total Price']";
    private static final String STATUS_OUTPUT_SELECTOR = "[aria-label='Display Status']";
    private static final String CANCEL_ORDER_OUTPUT_SELECTOR = "[aria-label='Cancel Order']";

    private static final String ORDER_DETAILS_HEADING_TEXT = "Order Details";

    public OrderHistoryPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputOrderNumber(String orderNumber) {
        pageClient.fill(ORDER_NUMBER_INPUT_SELECTOR, orderNumber);
    }

    public void clickSearch() {
        pageClient.click(SEARCH_BUTTON_SELECTOR);
    }

    public void waitForOrderDetails() {
        var orderDetailsText = pageClient.readTextContent(ORDER_DETAILS_CONTAINER_SELECTOR);
        assertTrue(orderDetailsText.contains(ORDER_DETAILS_HEADING_TEXT), "Should display order details heading");
    }

    public boolean hasOrderDetails() {
        try {
            waitForOrderDetails();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getOrderNumber() {
        return pageClient.readInputValue(ORDER_NUMBER_OUTPUT_SELECTOR);
    }

    public String getSku() {
        return pageClient.readInputValue(SKU_OUTPUT_SELECTOR);
    }

    public String getCountry() {
        return pageClient.readInputValue(COUNTRY_OUTPUT_SELECTOR);
    }

    public int getQuantity() {
        return pageClient.readInputInteger(QUANTITY_OUTPUT_SELECTOR);
    }

    public BigDecimal getUnitPrice() {
        return pageClient.readInputMoney(UNIT_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getOriginalPrice() {
        return pageClient.readInputMoney(ORIGINAL_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getDiscountRate() {
        return pageClient.readInputPercentage(DISCOUNT_RATE_OUTPUT_SELECTOR);
    }

    public BigDecimal getDiscountAmount() {
        return pageClient.readInputMoney(DISCOUNT_AMOUNT_OUTPUT_SELECTOR);
    }

    public BigDecimal getSubtotalPrice() {
        return pageClient.readInputMoney(SUBTOTAL_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getTaxRate() {
        return pageClient.readInputPercentage(TAX_RATE_OUTPUT_SELECTOR);
    }

    public BigDecimal getTaxAmount() {
        return pageClient.readInputMoney(TAX_AMOUNT_OUTPUT_SELECTOR);
    }

    public BigDecimal getTotalPrice() {
        return pageClient.readInputMoney(TOTAL_PRICE_OUTPUT_SELECTOR);
    }

    public OrderStatus getStatus() {
        var status = pageClient.readInputValue(STATUS_OUTPUT_SELECTOR);
        return OrderStatus.valueOf(status);
    }

    public void clickCancelOrder() {
        pageClient.click(CANCEL_ORDER_OUTPUT_SELECTOR);
        pageClient.waitForHidden(CANCEL_ORDER_OUTPUT_SELECTOR);
    }

    public boolean isCancelButtonHidden() {
        return pageClient.isHidden(CANCEL_ORDER_OUTPUT_SELECTOR);
    }
}

