package com.optivem.eshop.systemtest.core.clients.external.erp.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.commons.TestHttpUtils;
import com.optivem.eshop.systemtest.core.clients.external.erp.dtos.CreateProductRequest;
import com.optivem.eshop.systemtest.core.drivers.system.Result;

public class ProductController {

    private static final String ENDPOINT = "/products";

    private final TestHttpClient httpClient;

    public ProductController(TestHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void> createProduct(String sku, String price) {
        var product = new CreateProductRequest();
        product.setId(sku);
        product.setTitle("Test product title for " + sku);
        product.setDescription("Test product description for " + sku);
        product.setPrice(price);
        product.setCategory("Test Category");
        product.setBrand("Test Brand");

        var httpResponse = httpClient.post(ENDPOINT, product);

        return TestHttpUtils.getCreatedResultOrFailure(httpResponse);
    }
}
