package com.optivem.eshop.systemtest.core.erp.client.real.dtos.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductRequest {
    private String id;
    private String title;
    private String description;
    private String price;
    private String category;
    private String brand;
}