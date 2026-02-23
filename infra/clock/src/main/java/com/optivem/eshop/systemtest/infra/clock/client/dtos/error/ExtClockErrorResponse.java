package com.optivem.eshop.systemtest.infra.clock.client.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtClockErrorResponse {
    private String message;
}
