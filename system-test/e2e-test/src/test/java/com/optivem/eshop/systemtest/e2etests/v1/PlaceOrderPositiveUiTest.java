package com.optivem.eshop.systemtest.e2etests.v1;

import com.optivem.eshop.systemtest.base.v1.BaseRawTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;

import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.*;
import static org.assertj.core.api.Assertions.assertThat;

class PlaceOrderPositiveUiTest extends BaseRawTest {

    @BeforeEach
    void setUp() {
        setUpShopBrowser();
        setUpExternalHttpClients();
    }

    @Test
    void shouldPlaceOrderWithCorrectSubtotalPrice() throws Exception {
        // Given - Create product in ERP
        var createProductJson = """
                {
                    "id": "%s",
                    "title": "Test Product",
                    "description": "Test Description",
                    "category": "Test Category",
                    "brand": "Test Brand",
                    "price": "20.00"
                }
                """.formatted(SKU);

        var createProductUri = URI.create(getErpBaseUrl() + "/api/products");
        var createProductRequest = HttpRequest.newBuilder()
                .uri(createProductUri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createProductJson))
                .build();

        var createProductResponse = erpHttpClient.send(createProductRequest, HttpResponse.BodyHandlers.ofString());
        assertThat(createProductResponse.statusCode()).isEqualTo(201);

        // When - Place order using UI
        shopUiPage.navigate(getShopUiBaseUrl());
        shopUiPage.locator("a[href='/shop']").click();

        shopUiPage.locator("[aria-label=\"SKU\"]").fill(SKU);
        shopUiPage.locator("[aria-label=\"Quantity\"]").fill("5");
        shopUiPage.locator("[aria-label=\"Country\"]").fill(COUNTRY);
        shopUiPage.locator("[aria-label=\"Place Order\"]").click();

        // Extract order number from success message
        var successMessageText = shopUiPage.locator("[role='alert']").textContent();
        var pattern = Pattern.compile("Success! Order has been created with Order Number ([\\w-]+)");
        var matcher = pattern.matcher(successMessageText);
        assertThat(matcher.find()).isTrue();
        var orderNumber = matcher.group(1);

        // Then - View order and verify subtotal
        shopUiPage.locator("a[href='/order-history']").click();
        shopUiPage.locator("[aria-label='Order Number']").fill(orderNumber);
        shopUiPage.locator("[aria-label='Refresh Order List']").click();

        var rowSelector = String.format("//tr[contains(., '%s')]", orderNumber);
        assertThat(shopUiPage.locator(rowSelector).isVisible()).isTrue();

        var viewDetailsSelector = String.format("%s//a[contains(text(), 'View Details')]", rowSelector);
        shopUiPage.locator(viewDetailsSelector).click();

        var subtotalText = shopUiPage.locator("[aria-label='Display Subtotal Price']").textContent();
        var subtotalValue = Double.parseDouble(subtotalText.replace("$", ""));
        assertThat(subtotalValue).isEqualTo(100.00);
    }

    @Test
    void shouldPlaceOrder() throws Exception {
        // Given - Create product in ERP
        var createProductJson = """
                {
                    "id": "%s",
                    "title": "Test Product",
                    "description": "Test Description",
                    "category": "Test Category",
                    "brand": "Test Brand",
                    "price": "20.00"
                }
                """.formatted(SKU);

        var createProductUri = URI.create(getErpBaseUrl() + "/api/products");
        var createProductRequest = HttpRequest.newBuilder()
                .uri(createProductUri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createProductJson))
                .build();

        var createProductResponse = erpHttpClient.send(createProductRequest, HttpResponse.BodyHandlers.ofString());
        assertThat(createProductResponse.statusCode()).isEqualTo(201);

        // When - Place order using UI
        shopUiPage.navigate(getShopUiBaseUrl());
        shopUiPage.locator("a[href='/shop']").click();

        shopUiPage.locator("[aria-label=\"SKU\"]").fill(SKU);
        shopUiPage.locator("[aria-label=\"Quantity\"]").fill("5");
        shopUiPage.locator("[aria-label=\"Country\"]").fill(COUNTRY);
        shopUiPage.locator("[aria-label=\"Place Order\"]").click();

        // Extract order number from success message
        var successMessageText = shopUiPage.locator("[role='alert']").textContent();
        var pattern = Pattern.compile("Success! Order has been created with Order Number ([\\w-]+)");
        var matcher = pattern.matcher(successMessageText);
        assertThat(matcher.find()).isTrue();
        var orderNumber = matcher.group(1);
        assertThat(orderNumber).startsWith("ORD-");

        // Then - View order and verify all fields
        shopUiPage.locator("a[href='/order-history']").click();
        shopUiPage.locator("[aria-label='Order Number']").fill(orderNumber);
        shopUiPage.locator("[aria-label='Refresh Order List']").click();

        var rowSelector = String.format("//tr[contains(., '%s')]", orderNumber);
        assertThat(shopUiPage.locator(rowSelector).isVisible()).isTrue();

        var viewDetailsSelector = String.format("%s//a[contains(text(), 'View Details')]", rowSelector);
        shopUiPage.locator(viewDetailsSelector).click();

        assertThat(shopUiPage.locator("[aria-label='Display Order Number']").textContent()).isEqualTo(orderNumber);
        assertThat(shopUiPage.locator("[aria-label='Display SKU']").textContent()).isEqualTo(SKU);
        assertThat(shopUiPage.locator("[aria-label='Display Country']").textContent()).isEqualTo(COUNTRY);
        assertThat(Integer.parseInt(shopUiPage.locator("[aria-label='Display Quantity']").textContent())).isEqualTo(5);
        
        var unitPriceText = shopUiPage.locator("[aria-label='Display Unit Price']").textContent().replace("$", "");
        assertThat(Double.parseDouble(unitPriceText)).isEqualTo(20.00);
        
        var subtotalText = shopUiPage.locator("[aria-label='Display Subtotal Price']").textContent().replace("$", "");
        assertThat(Double.parseDouble(subtotalText)).isEqualTo(100.00);
        
        assertThat(shopUiPage.locator("[aria-label='Display Status']").textContent()).isEqualTo("PLACED");
        
        var discountRateText = shopUiPage.locator("[aria-label='Display Discount Rate']").textContent().replace("%", "");
        assertThat(Double.parseDouble(discountRateText)).isGreaterThanOrEqualTo(0.0);
        
        var discountAmountText = shopUiPage.locator("[aria-label='Display Discount Amount']").textContent().replace("$", "");
        assertThat(Double.parseDouble(discountAmountText)).isGreaterThanOrEqualTo(0.0);
        
        var taxRateText = shopUiPage.locator("[aria-label='Display Tax Rate']").textContent().replace("%", "");
        assertThat(Double.parseDouble(taxRateText)).isGreaterThanOrEqualTo(0.0);
        
        var taxAmountText = shopUiPage.locator("[aria-label='Display Tax Amount']").textContent().replace("$", "");
        assertThat(Double.parseDouble(taxAmountText)).isGreaterThanOrEqualTo(0.0);
        
        var totalPriceText = shopUiPage.locator("[aria-label='Display Total Price']").textContent().replace("$", "");
        assertThat(Double.parseDouble(totalPriceText)).isGreaterThan(0.0);
    }
}
