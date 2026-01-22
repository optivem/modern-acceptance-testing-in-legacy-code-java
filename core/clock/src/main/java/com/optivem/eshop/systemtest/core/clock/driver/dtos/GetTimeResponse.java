package com.optivem.eshop.systemtest.core.clock.driver.dtos;

import com.optivem.eshop.systemtest.core.clock.client.dtos.ExtGetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.client.dtos.error.ExtClockErrorResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
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

    public static GetTimeResponse from(ExtGetTimeResponse response) {
        return GetTimeResponse.builder().time(response.getTime()).build();
    }
}
