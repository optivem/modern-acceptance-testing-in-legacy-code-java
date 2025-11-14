package com.optivem.eshop.systemtest.e2etests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.e2etests.helpers.ErpApiHelper;
import lombok.Data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ApiE2eTest {

    private static final String BASE_URL = TestConfiguration.getBaseUrl();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newHttpClient();
    }

    @AfterEach
    void tearDown() {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    @Test
    void placeOrder_shouldReturnOrderNumber() throws Exception {
        // Arrange - Set up product in ERP
        String baseSku = "AUTO-PO-100";
        BigDecimal unitPrice = new BigDecimal("199.99");
        int quantity = 5;

        String sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        var requestDto = new PlaceOrderRequest();
        requestDto.setSku(sku);
        requestDto.setQuantity(String.valueOf(quantity));
        requestDto.setCountry("US");

        var requestBody = objectMapper.writeValueAsString(requestDto);
        
        var request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Act
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(201, response.statusCode(), "Response status should be 201 CREATED. Response body: " + response.body());

        var responseBody = response.body();
        var responseDto = objectMapper.readValue(responseBody, PlaceOrderResponse.class);
        
        // Verify response contains orderNumber
        assertNotNull(responseDto.getOrderNumber(), "Order number should not be null");
        assertTrue(responseDto.getOrderNumber().startsWith("ORD-"), "Order number should start with ORD-");
    }

    @Test
    void getOrder_shouldReturnOrderDetails() throws Exception {
        // Arrange - Set up product in ERP first
        String baseSku = "AUTO-GO-200";
        BigDecimal unitPrice = new BigDecimal("299.50");
        int quantity = 3;
        String country = "DE";

        String sku = setupProductInErpAndGetSku(baseSku, "Test Laptop", unitPrice);

        var placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setSku(sku);
        placeOrderRequest.setQuantity(String.valueOf(quantity));
        placeOrderRequest.setCountry(country);

        var requestBody = objectMapper.writeValueAsString(placeOrderRequest);
        
        var postRequest = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, postResponse.statusCode(), "Order creation should return 201 CREATED. Response body: " + postResponse.body());

        var placeOrderResponse = objectMapper.readValue(postResponse.body(), PlaceOrderResponse.class);
        var orderNumber = placeOrderResponse.getOrderNumber();
        
        // Act - Get the order details
        var getRequest = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders/" + orderNumber))
                .GET()
                .build();

        var getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(200, getResponse.statusCode(), "Response status should be 200 OK");
        
        var getOrderResponse = objectMapper.readValue(getResponse.body(), GetOrderResponse.class);
        
        // Assert all fields from GetOrderResponse
        assertNotNull(getOrderResponse.getOrderNumber(), "Order number should not be null");
        assertEquals(sku, getOrderResponse.getSku(), "SKU should be " + sku);
        assertEquals(quantity, getOrderResponse.getQuantity(), "Quantity should be " + quantity);
        assertEquals(country, getOrderResponse.getCountry(), "Country should be " + country);
        assertEquals(unitPrice, getOrderResponse.getUnitPrice(), "Unit price should be " + unitPrice);

        BigDecimal expectedOriginalPrice = new BigDecimal("898.50");
        assertEquals(expectedOriginalPrice, getOrderResponse.getOriginalPrice(),
                "Original price should be " + expectedOriginalPrice);

        assertNotNull(getOrderResponse.getStatus(), "Status should not be null");
        assertEquals("PLACED", getOrderResponse.getStatus(), "Status should be PLACED");
    }

    @Test
    void cancelOrder_shouldSetStatusToCancelled() throws Exception {
        // Arrange - Place an order
        var placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setSku("HUA-P30");
        placeOrderRequest.setQuantity("2");
        placeOrderRequest.setCountry("UK");

        var requestBody = objectMapper.writeValueAsString(placeOrderRequest);
        
        var postRequest = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        // Verify order was created successfully
        assertEquals(201, postResponse.statusCode(), "Order creation should return 201 CREATED. Response: " + postResponse.body());

        var placeOrderResponse = objectMapper.readValue(postResponse.body(), PlaceOrderResponse.class);
        var orderNumber = placeOrderResponse.getOrderNumber();
        
        assertNotNull(orderNumber, "Order number should not be null");

        // Act - Cancel the order
        var cancelRequest = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders/" + orderNumber + "/cancel"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        var cancelResponse = httpClient.send(cancelRequest, HttpResponse.BodyHandlers.ofString());

        // Assert - Verify cancel response
        assertEquals(204, cancelResponse.statusCode(), "Response status should be 204 No Content");

        // Verify order status is CANCELLED
        var getRequest = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders/" + orderNumber))
                .GET()
                .build();

        var getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode(), "Response status should be 200 OK");
        
        var getOrderResponse = objectMapper.readValue(getResponse.body(), GetOrderResponse.class);
        assertEquals("CANCELLED", getOrderResponse.getStatus(), "Order status should be CANCELLED");
    }


    @Test
    void shouldRejectOrderWithNonExistentSku() throws Exception {
        // Arrange
        var requestDto = new PlaceOrderRequest();
        requestDto.setSku("NON-EXISTENT-SKU-12345");
        requestDto.setQuantity("5");
        requestDto.setCountry("US");

        var requestBody = objectMapper.writeValueAsString(requestDto);

        var request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Act
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(422, response.statusCode(), "Response status should be 422 Unprocessable Entity");

        var responseBody = response.body();
        assertTrue(responseBody.contains("Product does not exist for SKU"),
                "Error message should contain 'Product does not exist for SKU'. Actual: " + responseBody);
    }

    @Test
    void shouldRejectOrderWithNegativeQuantity() throws Exception {
        // Arrange - Set up product in ERP first
        String baseSku = "AUTO-NQ-400";
        BigDecimal unitPrice = new BigDecimal("99.99");

        String sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        var requestDto = new PlaceOrderRequest();
        requestDto.setSku(sku);
        requestDto.setQuantity("-5");
        requestDto.setCountry("US");

        var requestBody = objectMapper.writeValueAsString(requestDto);

        var request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Act
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(422, response.statusCode(), "Response status should be 422 Unprocessable Entity");

        var responseBody = response.body();
        assertTrue(responseBody.contains("Quantity must be positive"),
                "Error message should contain 'Quantity must be positive'. Actual: " + responseBody);
    }

    private static Stream<Arguments> provideEmptySkuValues() {
        return Stream.of(
                Arguments.of((String) null),  // Null value
                Arguments.of(""),             // Empty string
                Arguments.of("   ")           // Whitespace string
        );
    }


    private static Stream<Arguments> provideEmptyQuantityValues() {
        return Stream.of(
                Arguments.of((String) null),  // Null value
                Arguments.of(""),             // Empty string
                Arguments.of("   ")           // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyQuantityValues")
    void shouldRejectOrderWithEmptyQuantity(String quantityValue) throws Exception {
        // Arrange - Set up product in ERP first
        String baseSku = "AUTO-EQ-500";
        BigDecimal unitPrice = new BigDecimal("150.00");

        String sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        var requestDto = new PlaceOrderRequest();
        requestDto.setSku(sku);
        requestDto.setQuantity(quantityValue);
        requestDto.setCountry("US");

        var requestBody = objectMapper.writeValueAsString(requestDto);

        var request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Act
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        // Assert
        assertEquals(422, response.statusCode(), "Response status should be 422 Unprocessable Entity for Quantity: " + quantityValue);

        var responseBody = response.body();
        assertTrue(responseBody.contains("Quantity must not be empty"),
                "Error message should be 'Quantity must not be empty'. Actual: " + responseBody);
    }

    private static Stream<Arguments> provideNonIntegerQuantityValues() {
        return Stream.of(
                Arguments.of("3.5"),    // Decimal value
                Arguments.of("lala")    // String value
        );
    }



    private static Stream<Arguments> provideEmptyCountryValues() {
        return Stream.of(
                Arguments.of((String) null),  // Null value
                Arguments.of(""),             // Empty string
                Arguments.of("   ")           // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyCountryValues")
    void shouldRejectOrderWithEmptyCountry(String countryValue) throws Exception {
        // Arrange - Set up product in ERP first and get unique SKU
        String baseSku = "AUTO-EC-700";
        BigDecimal unitPrice = new BigDecimal("225.00");

        String sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        var requestDto = new PlaceOrderRequest();
        requestDto.setSku(sku);
        requestDto.setQuantity("5");
        requestDto.setCountry(countryValue);

        var requestBody = objectMapper.writeValueAsString(requestDto);

        var request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/api/orders"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Act
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(422, response.statusCode(), "Response status should be 422 Unprocessable Entity for Country: " + countryValue);

        var responseBody = response.body();
        assertTrue(responseBody.contains("Country must not be empty"),
                "Error message should be 'Country must not be empty'. Actual: " + responseBody);
    }


    // Helper method that returns the unique SKU for use in tests
    private String setupProductInErpAndGetSku(String baseSku, String title, BigDecimal price) throws Exception {
        return ErpApiHelper.setupProductInErp(httpClient, baseSku, title, price);
    }

    @Data
    static class PlaceOrderRequest {
        private String sku;
        private String quantity;
        private String country;
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PlaceOrderResponse {
        private String orderNumber;
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class GetOrderResponse {
        private String orderNumber;
        private String sku;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal originalPrice;
        private String status;
        private String country;
    }
}