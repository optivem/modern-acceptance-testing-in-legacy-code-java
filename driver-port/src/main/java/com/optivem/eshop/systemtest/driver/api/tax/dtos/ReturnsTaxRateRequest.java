package com.optivem.eshop.systemtest.driver.port.tax.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnsTaxRateRequest {
    private String country;
    private String taxRate;
}


