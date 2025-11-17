package com.optivem.eshop.systemtest.core.clients.external.erp.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.external.erp.dtos.CreateProductRequest;

import java.math.BigDecimal;

public class ProductController {

    private static final String ENDPOINT = "/products";

    private final TestHttpClient httpClient;

    public ProductController(TestHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String createProduct(String baseSku, BigDecimal price) {
        // Add UUID suffix to avoid duplicate IDs across test runs
        var uniqueSku = baseSku + "-" + java.util.UUID.randomUUID().toString().substring(0, 8);

        var product = new CreateProductRequest();
        product.setId(uniqueSku);
        product.setTitle("Test product title for " + uniqueSku);
        product.setDescription("Test product description for " + uniqueSku);
        product.setPrice(price);
        product.setCategory("Test Category");
        product.setBrand("Test Brand");

        var httpResponse = httpClient.post(ENDPOINT, product);
        httpClient.assertCreated(httpResponse);
        return uniqueSku;
    }
}
