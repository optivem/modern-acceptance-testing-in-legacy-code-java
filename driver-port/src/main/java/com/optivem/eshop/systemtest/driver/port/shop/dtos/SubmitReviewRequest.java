package com.optivem.eshop.systemtest.driver.port.shop.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitReviewRequest {
    private String orderNumber;
    private String rating;
    private String comment;
}
