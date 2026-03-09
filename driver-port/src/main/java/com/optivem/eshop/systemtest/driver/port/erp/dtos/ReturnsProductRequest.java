package com.optivem.eshop.systemtest.driver.port.erp.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReturnsProductRequest {
    private String sku;
    private String price;
    private String reviewable;
}

