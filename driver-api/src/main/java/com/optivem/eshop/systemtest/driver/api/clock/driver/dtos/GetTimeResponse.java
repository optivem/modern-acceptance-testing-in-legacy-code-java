package com.optivem.eshop.systemtest.driver.api.clock.driver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTimeResponse {
    Instant time;
}
