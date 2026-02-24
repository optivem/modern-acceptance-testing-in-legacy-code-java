package com.optivem.eshop.systemtest.driver.api.clock.driver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnsTimeRequest {
    String time;
}
