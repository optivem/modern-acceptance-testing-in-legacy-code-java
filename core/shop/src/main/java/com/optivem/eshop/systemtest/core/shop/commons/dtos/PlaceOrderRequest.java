package com.optivem.eshop.systemtest.core.shop.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest {
    private String sku;
    private String quantity;
    private String country;
    private String couponCode;
}

