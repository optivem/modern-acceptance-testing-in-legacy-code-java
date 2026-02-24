package com.optivem.eshop.systemtest.core.erp.driver.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductRequest {
    private String sku;
}

