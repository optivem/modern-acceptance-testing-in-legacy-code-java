package com.optivem.eshop.systemtest.core.clients.external.erp.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.BaseController;
import com.optivem.eshop.systemtest.core.clients.external.erp.dtos.ErpProduct;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductController extends BaseController {
    public ProductController(HttpClient httpClient, String baseUrl) {
        super(httpClient, baseUrl);
    }

    public String create(String baseSku, String title, BigDecimal price) {
        try {
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

            // JSON Server returns 201 Created for successful resource creation
            assertTrue(response.statusCode() == 201,
                    "ERP product setup should succeed. Status: " + response.statusCode() + ", Body: " + response.body());

            return uniqueSku;
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up ERP product", e);
        }
    }
}
