package com.optivem.eshop.systemtest.driver.api.clock;

import com.optivem.eshop.systemtest.driver.api.clock.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Result;

public interface ClockDriver extends AutoCloseable {
    Result<Void, ClockErrorResponse> goToClock();
    Result<GetTimeResponse, ClockErrorResponse> getTime();
    Result<Void, ClockErrorResponse> returnsTime(ReturnsTimeRequest request);
}

