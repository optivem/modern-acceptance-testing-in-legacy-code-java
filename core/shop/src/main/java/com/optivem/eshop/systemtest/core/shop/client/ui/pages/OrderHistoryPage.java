package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.playwright.PageClient;

public class OrderHistoryPage extends BasePage {
    private static final String ORDER_NUMBER_INPUT_SELECTOR = "[aria-label='Order Number']";
    private static final String SEARCH_BUTTON_SELECTOR = "[aria-label='Search']";

    public OrderHistoryPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputOrderNumber(String orderNumber) {
        pageClient.setInputValue(ORDER_NUMBER_INPUT_SELECTOR, orderNumber);
    }

    public void clickSearch() {
        pageClient.click(SEARCH_BUTTON_SELECTOR);
    }

    public boolean isOrderListed(String orderNumber) {
        var rowSelector = getRowSelector(orderNumber);
        System.out.println("[DEBUG] Looking for order row with selector: " + rowSelector);
        
        // Debug: check what's actually on the page
        try {
            var allRows = pageClient.readAllTextContentsWithoutWait("table tbody tr");
            System.out.println("[DEBUG] Found " + allRows.size() + " rows in table");
            for (int i = 0; i < Math.min(3, allRows.size()); i++) {
                System.out.println("[DEBUG] Row " + i + ": " + allRows.get(i));
            }
        } catch (Exception e) {
            System.out.println("[DEBUG] Could not read table rows: " + e.getMessage());
        }
        
        return pageClient.exists(rowSelector);
    }

    public OrderDetailsPage clickViewOrderDetails(String orderNumber) {
        var rowSelector = getRowSelector(orderNumber);
        // Find the link by its text content instead of aria-label
        var viewDetailsLinkSelector = rowSelector + "//a[contains(text(), 'View Details')]";
        pageClient.click(viewDetailsLinkSelector);
        return new OrderDetailsPage(pageClient);
    }

    private String getRowSelector(String orderNumber) {
        // Simpler selector: find any row that contains the order number text
        return String.format("//tr[contains(., '%s')]", orderNumber);
    }


}
