package com.optivem.eshop.systemtest.driver.api.shop.dtos.coupons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishCouponRequest {
    private String code;
    private String discountRate;
    private String validFrom;
    private String validTo;
    private String usageLimit;
}
