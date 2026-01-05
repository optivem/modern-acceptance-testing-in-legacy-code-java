package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.CouponDto;
import com.optivem.playwright.PageClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CouponManagementPage extends BasePage {

    private static final String COUPON_CODE_INPUT_SELECTOR = "[aria-label=\"Coupon Code\"]";
    private static final String DISCOUNT_RATE_INPUT_SELECTOR = "[aria-label=\"Discount Rate\"]";
    private static final String VALID_FROM_INPUT_SELECTOR = "[aria-label=\"Valid From\"]";
    private static final String VALID_TO_INPUT_SELECTOR = "[aria-label=\"Valid To\"]";
    private static final String USAGE_LIMIT_INPUT_SELECTOR = "[aria-label=\"Usage Limit\"]";
    private static final String PUBLISH_COUPON_BUTTON_SELECTOR = "[aria-label=\"Create Coupon\"]";
    
    // Selectors for browsing coupons
    private static final String COUPONS_TABLE_SELECTOR = "[aria-label=\"Coupons Table\"]";

    public CouponManagementPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputCouponCode(String couponCode) {
        pageClient.setInputValue(COUPON_CODE_INPUT_SELECTOR, couponCode);
    }

    public void inputDiscountRate(String discountRate) {
        pageClient.setInputValue(DISCOUNT_RATE_INPUT_SELECTOR, discountRate);
    }

    public void inputValidFrom(String validFrom) {
        var datetimeValue = getValidFromDateTimeString(validFrom);
        pageClient.setInputValue(VALID_FROM_INPUT_SELECTOR, datetimeValue);
    }

    private static String getValidFromDateTimeString(String validFrom) {
        if(validFrom == null || validFrom.isEmpty()) {
            return "";
        }

        // Extract date from ISO 8601 format (2024-06-01T00:00:00Z -> 2024-06-01)
        // Then convert to datetime-local format (2024-06-01T00:00) for HTML input
        String dateOnly = validFrom.substring(0, 10); // YYYY-MM-DD
        return dateOnly + "T00:00";
    }

    public void inputValidTo(String validTo) {
        var datetimeValue = getValidToDateTimeString(validTo);
        pageClient.setInputValue(VALID_TO_INPUT_SELECTOR, datetimeValue);
    }

    private static String getValidToDateTimeString(String validTo) {
        if(validTo == null || validTo.isEmpty()) {
            return "";
        }

        // Extract date from ISO 8601 format (2024-08-31T23:59:59Z -> 2024-08-31)
        // Then convert to datetime-local format (2024-08-31T23:59) for HTML input
        String dateOnly = validTo.substring(0, 10); // YYYY-MM-DD
        return dateOnly + "T23:59";
    }

    public void inputUsageLimit(String usageLimit) {
        pageClient.setInputValue(USAGE_LIMIT_INPUT_SELECTOR, usageLimit);
    }

    public void clickPublishCoupon() {
        pageClient.click(PUBLISH_COUPON_BUTTON_SELECTOR);
    }

    public boolean hasSuccessCouponNotification() {
        return hasSuccessNotification();
    }
    
    public boolean hasCouponsTable() {
        return pageClient.exists(COUPONS_TABLE_SELECTOR);
    }
    
    public List<CouponDto> readCoupons() {
        if (!hasCouponsTable()) {
            return new ArrayList<>();
        }
        
        // Wait for React to render table rows - give it time to populate
        try {
            pageClient.waitForVisible("table.table tbody tr");
        } catch (Exception e) {
            // No rows visible, return empty list
            return new ArrayList<>();
        }
        
        var coupons = new ArrayList<CouponDto>();

        // Use readAllTextContentsWithoutWait to avoid strict mode violations
        // These selectors intentionally match multiple elements (one per table row)
        var codes = pageClient.readAllTextContentsWithoutWait("table.table tbody tr td:nth-child(1)");
        var discountRates = pageClient.readAllTextContentsWithoutWait("table.table tbody tr td:nth-child(2)");
        var validFroms = pageClient.readAllTextContentsWithoutWait("table.table tbody tr td:nth-child(3)");
        var validTos = pageClient.readAllTextContentsWithoutWait("table.table tbody tr td:nth-child(4)");
        var usageLimits = pageClient.readAllTextContentsWithoutWait("table.table tbody tr td:nth-child(5)");
        var usedCounts = pageClient.readAllTextContentsWithoutWait("table.table tbody tr td:nth-child(6)");

        var rowCount = codes.size();
        
        // Double-check we have data before trying to access it
        if (rowCount == 0) {
            return new ArrayList<>();
        }

        // Build coupon objects from the collected data
        for (int i = 0; i < rowCount; i++) {
            var code = codes.get(i).trim();
            var discountRateText = discountRates.get(i).trim().replace("%", "");
            var validFromText = validFroms.get(i).trim();
            var validToText = validTos.get(i).trim();
            var usageLimitText = usageLimits.get(i).trim();
            var usedCountText = usedCounts.get(i).trim();

            var coupon = CouponDto.builder()
                    .code(code)
                    .discountRate(parseDiscountRate(discountRateText))
                    .validFrom(parseInstant(validFromText))
                    .validTo(parseInstant(validToText))
                    .usageLimit(parseUsageLimit(usageLimitText))
                    .usedCount(Integer.parseInt(usedCountText))
                    .build();

            coupons.add(coupon);
        }

        return coupons;
    }

    private double parseDiscountRate(String text) {
        try {
            return Double.parseDouble(text) / 100.0; // Convert percentage to decimal
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Instant parseInstant(String text) {
        if (text == null || text.equalsIgnoreCase("Immediate") || text.equalsIgnoreCase("Never") || text.isEmpty()) {
            return null;
        }
        try {
            // Try to parse as ISO format first
            return Instant.parse(text);
        } catch (Exception e) {
            // If it fails, return null (UI might show formatted date)
            return null;
        }
    }

    private Integer parseUsageLimit(String text) {
        if (text == null || text.equalsIgnoreCase("Unlimited") || text.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

