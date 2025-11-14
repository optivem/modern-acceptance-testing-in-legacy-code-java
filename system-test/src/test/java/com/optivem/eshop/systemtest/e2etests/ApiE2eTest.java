package com.optivem.eshop.systemtest.e2etests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optivem.eshop.systemtest.TestConfiguration;
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
        // Arrange
        var requestDto = new PlaceOrderRequest();
        requestDto.setSku("HP-15");
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
        assertEquals(201, response.statusCode(), "Response status should be 201 CREATED. Response body: " + response.body());

        var responseBody = response.body();
        var responseDto = objectMapper.readValue(responseBody, PlaceOrderResponse.class);
        
        // Verify response contains orderNumber
        assertNotNull(responseDto.getOrderNumber(), "Order number should not be null");
        assertTrue(responseDto.getOrderNumber().startsWith("ORD-"), "Order number should start with ORD-");
    }

    @Test
    void getOrder_shouldReturnOrderDetails() throws Exception {
        // Arrange - First place an order
        String sku = "SAM-2020";
        int quantity = 3;
        String country = "DE";

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
        
        assertNotNull(getOrderResponse.getOrderNumber(), "Order number should not be null");
        assertEquals(sku, getOrderResponse.getSku(), "SKU should be " + sku);
        assertEquals(quantity, getOrderResponse.getQuantity(), "Quantity should be " + quantity);
        assertEquals(country, getOrderResponse.getCountry(), "Country should be " + country);

        assertNotNull(getOrderResponse.getUnitPrice(), "Unit price should not be null");
        assertNotNull(getOrderResponse.getOriginalPrice(), "Total price should not be null");
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
        // Arrange
        var requestDto = new PlaceOrderRequest();
        requestDto.setSku("HP-15");
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
                "Error message should be 'Quantity must be positive'. Actual: " + responseBody);
    }

    private static Stream<Arguments> provideEmptySkuValues() {
        return Stream.of(
                Arguments.of((String) null),  // Null value
                Arguments.of(""),             // Empty string
                Arguments.of("   ")           // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptySkuValues")
    void shouldRejectOrderWithEmptySku(String skuValue) throws Exception {
        // Arrange
        var requestDto = new PlaceOrderRequest();
        requestDto.setSku(skuValue);
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
        assertEquals(422, response.statusCode(), "Response status should be 422 Unprocessable Entity for SKU: " + skuValue);

        var responseBody = response.body();
        assertTrue(responseBody.contains("SKU must not be empty"),
                "Error message should be 'SKU must not be empty'. Actual: " + responseBody);
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
        // Arrange
        var requestDto = new PlaceOrderRequest();
        requestDto.setSku("HP-15");
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


    @ParameterizedTest
    @MethodSource("provideNonIntegerQuantityValues")
    void shouldRejectOrderWithNonIntegerQuantity(String quantityValue) throws Exception {
        // Arrange
        var requestDto = new PlaceOrderRequest();
        requestDto.setSku("HP-15");
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
        assertEquals(400, response.statusCode(), "Response status should be 400 Bad Request for quantity: " + quantityValue);

        var responseBody = response.body();
        assertTrue(responseBody.contains("Quantity must be an integer"),
                "Error message should be 'Quantity must be an integer'. Actual: " + responseBody);
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


    // Helper method to set up product in ERP JSON Server
    private void setupProductInErp(String sku, String title, BigDecimal price) throws Exception {
        // Add UUID suffix to avoid duplicate IDs across test runs
        String uniqueSku = sku + "-" + java.util.UUID.randomUUID().toString().substring(0, 8);

        var product = new ErpProduct();
        product.setId(uniqueSku);
        product.setTitle(title);
        product.setDescription("Test product for " + uniqueSku);
        product.setPrice(price);
        product.setCategory("test-category");
        product.setBrand("Test Brand");

        var productJson = objectMapper.writeValueAsString(product);

        var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3000/products"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(productJson))
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // JSON Server returns 201 for successful creation
        assertTrue(response.statusCode() == 201 || response.statusCode() == 200,
                "ERP product setup should succeed. Status: " + response.statusCode() + ", Body: " + response.body());
    }

    // Helper method that returns the unique SKU for use in tests
    private String setupProductInErpAndGetSku(String baseSku, String title, BigDecimal price) throws Exception {
        // Add UUID suffix to avoid duplicate IDs across test runs
        String uniqueSku = baseSku + "-" + java.util.UUID.randomUUID().toString().substring(0, 8);

        var product = new ErpProduct();
        product.setId(uniqueSku);
        product.setTitle(title);
        product.setDescription("Test product for " + uniqueSku);
        product.setPrice(price);
        product.setCategory("test-category");
        product.setBrand("Test Brand");

        var productJson = objectMapper.writeValueAsString(product);

        var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3000/products"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(productJson))
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // JSON Server returns 201 for successful creation
        assertTrue(response.statusCode() == 201 || response.statusCode() == 200,
                "ERP product setup should succeed. Status: " + response.statusCode() + ", Body: " + response.body());

        return uniqueSku;
    }

    @Data
    static class ErpProduct {
        private String id;
        private String title;
        private String description;
        private BigDecimal price;
        private String category;
        private String brand;
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