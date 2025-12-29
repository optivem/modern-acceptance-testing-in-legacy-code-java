package com.optivem.eshop.systemtest.core.shop.client.dtos;

import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {
    private String orderNumber;
    private String sku;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotalPrice;
    private BigDecimal discountRate;
    private BigDecimal discountAmount;
    private BigDecimal preTaxTotal;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String country;
}

