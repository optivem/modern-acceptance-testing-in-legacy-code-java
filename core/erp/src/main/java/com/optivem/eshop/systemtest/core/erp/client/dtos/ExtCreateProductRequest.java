package com.optivem.eshop.systemtest.core.erp.client.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExtCreateProductRequest {
    private String id;
    private String title;
    private String description;
    private String price;
    private String category;
    private String brand;
}