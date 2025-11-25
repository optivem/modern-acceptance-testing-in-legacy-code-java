package com.optivem.eshop.systemtest.core.clients.system.ui.pages;

import com.optivem.eshop.systemtest.core.clients.commons.TestPageClient;
import com.optivem.eshop.systemtest.core.commons.dtos.enums.OrderStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderHistoryPage extends BasePage {

    private static final String ORDER_NUMBER_INPUT_SELECTOR = "[aria-label='Order Number']";
    private static final String SEARCH_BUTTON_SELECTOR = "[aria-label='Search']";
    private static final String ORDER_DETAILS_CONTAINER_SELECTOR = "#orderDetails";
    private static final String NOTIFICATION_ALERT_SELECTOR = "#notifications [role='alert'].notification";
    private static final String ORDER_NUMBER_OUTPUT_SELECTOR = "[aria-label='Display Order Number']";
    private static final String PRODUCT_ID_OUTPUT_SELECTOR = "[aria-label='Display Product ID']";
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

    public OrderHistoryPage(TestPageClient pageClient) {
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

    public String getOrderNumber() {
        return pageClient.readInputValue(ORDER_NUMBER_OUTPUT_SELECTOR);
    }

    public String getProductId() {
        return pageClient.readInputValue(PRODUCT_ID_OUTPUT_SELECTOR);
    }

    public String getCountry() {
        return pageClient.readInputValue(COUNTRY_OUTPUT_SELECTOR);
    }

    public String getQuantity() {
        return pageClient.readInputValue(QUANTITY_OUTPUT_SELECTOR);
    }

    public BigDecimal getUnitPrice() {
        return pageClient.readInputCurrencyDecimalValue(UNIT_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getOriginalPrice() {
        return pageClient.readInputCurrencyDecimalValue(ORIGINAL_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getDiscountRate() {
        return pageClient.readInputPercentageDecimalValue(DISCOUNT_RATE_OUTPUT_SELECTOR);
    }

    public BigDecimal getDiscountAmount() {
        return pageClient.readInputCurrencyDecimalValue(DISCOUNT_AMOUNT_OUTPUT_SELECTOR);
    }

    public BigDecimal getSubtotalPrice() {
        return pageClient.readInputCurrencyDecimalValue(SUBTOTAL_PRICE_OUTPUT_SELECTOR);
    }

    public BigDecimal getTaxRate() {
        return pageClient.readInputPercentageDecimalValue(TAX_RATE_OUTPUT_SELECTOR);
    }

    public BigDecimal getTaxAmount() {
        return pageClient.readInputCurrencyDecimalValue(TAX_AMOUNT_OUTPUT_SELECTOR);
    }

    public BigDecimal getTotalPrice() {
        return pageClient.readInputCurrencyDecimalValue(TOTAL_PRICE_OUTPUT_SELECTOR);
    }

    public OrderStatus getStatus() {
        var status = pageClient.readInputValue(STATUS_OUTPUT_SELECTOR);
        return OrderStatus.valueOf(status);
    }

    public void clickCancelOrder() {
        pageClient.getPage().onDialog(dialog -> {
            System.out.println("Dialog appeared: " + dialog.message());
            dialog.accept();
        });

        pageClient.click(CANCEL_ORDER_OUTPUT_SELECTOR);
        pageClient.waitForHidden(CANCEL_ORDER_OUTPUT_SELECTOR);
    }

    public String getNotificationMessage() {
        return pageClient.readTextContent(NOTIFICATION_ALERT_SELECTOR);
    }

    public void assertCancelButtonNotVisible() {
        assertTrue(pageClient.isHidden(CANCEL_ORDER_OUTPUT_SELECTOR), "Cancel Order button should not be visible");
    }
}

