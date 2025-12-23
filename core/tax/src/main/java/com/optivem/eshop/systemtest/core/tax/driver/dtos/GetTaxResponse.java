package com.optivem.eshop.systemtest.core.tax.driver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTaxResponse {
    private String country;
    private BigDecimal taxRate;
}

