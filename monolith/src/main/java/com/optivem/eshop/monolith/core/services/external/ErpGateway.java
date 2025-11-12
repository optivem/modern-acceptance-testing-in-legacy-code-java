package com.optivem.eshop.monolith.core.services.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optivem.eshop.monolith.core.dtos.external.ProductPriceResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class ErpGateway {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    @Value("${erp.url}")
    private String erpUrl;

    public BigDecimal getUnitPrice(long productId) {
        try {
            var httpClient = HttpClient.newBuilder()
                    .connectTimeout(java.time.Duration.ofSeconds(10))
                    .build();
                    
            var url = erpUrl + "/products/" + productId;
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(java.time.Duration.ofSeconds(10))
                    .GET()
                    .build();
                    
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("ERP API returned status " + response.statusCode() + 
                        " for product: " + productId + ". URL: " + url + ". Response: " + response.body());
            }

            var productPriceResponse = OBJECT_MAPPER.readValue(response.body(), ProductPriceResponse.class);

            return productPriceResponse.getPrice();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch price for product: " + productId + 
                    " from URL: " + erpUrl + "/products/" + productId + 
                    ". Error: " + e.getClass().getSimpleName() + ": " + e.getMessage(), e);
        }
    }
}
