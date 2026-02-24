package com.optivem.eshop.systemtest.driver.api.erp.driver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponse {
    private String sku;
    private BigDecimal price;
}
