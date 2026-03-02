package com.optivem.eshop.systemtest.driver.port.clock;

import com.optivem.eshop.systemtest.driver.port.clock.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.driver.port.clock.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.driver.port.clock.dtos.error.ClockErrorResponse;
import com.optivem.common.Result;

public interface ClockDriver extends AutoCloseable {
    Result<Void, ClockErrorResponse> goToClock();
    Result<GetTimeResponse, ClockErrorResponse> getTime();
    Result<Void, ClockErrorResponse> returnsTime(ReturnsTimeRequest request);
}

