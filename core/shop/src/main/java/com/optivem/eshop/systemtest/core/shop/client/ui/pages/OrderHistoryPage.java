package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.playwright.PageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderHistoryPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(OrderHistoryPage.class);
    private static final String ORDER_NUMBER_INPUT_SELECTOR = "[aria-label='Order Number']";
    private static final String SEARCH_BUTTON_SELECTOR = "[aria-label='Refresh Order List']";
    
    private static final String VIEW_DETAILS_LINK_TEXT = "View Details";
    private static final String ROW_SELECTOR_TEMPLATE = "//tr[contains(., '%s')]";

    public OrderHistoryPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputOrderNumber(String orderNumber) {
        pageClient.fill(ORDER_NUMBER_INPUT_SELECTOR, orderNumber);
    }

    public void clickSearch() {
        pageClient.click(SEARCH_BUTTON_SELECTOR);
    }

    public boolean isOrderListed(String orderNumber) {
        var rowSelector = getRowSelector(orderNumber);
        return pageClient.isVisible(rowSelector);
    }

    public OrderDetailsPage clickViewOrderDetails(String orderNumber) {
        var rowSelector = getRowSelector(orderNumber);
        // Find the link by its text content instead of aria-label
        var viewDetailsLinkSelector = rowSelector + "//a[contains(text(), '" + VIEW_DETAILS_LINK_TEXT + "')]";
        pageClient.click(viewDetailsLinkSelector);
        return new OrderDetailsPage(pageClient);
    }

    private String getRowSelector(String orderNumber) {
        // Simpler selector: find any row that contains the order number text
        return String.format(ROW_SELECTOR_TEMPLATE, orderNumber);
    }


}
