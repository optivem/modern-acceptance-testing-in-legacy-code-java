package com.optivem.eshop.systemtest.core.clients.external.erp.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ErpProduct {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String brand;
}