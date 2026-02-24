package com.optivem.eshop.systemtest.driver.api.clock.driver;

import com.optivem.eshop.systemtest.driver.api.clock.driver.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.driver.api.clock.driver.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.driver.api.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Result;

public interface ClockDriver extends AutoCloseable {
    Result<Void, ClockErrorResponse> goToClock();
    Result<GetTimeResponse, ClockErrorResponse> getTime();
    Result<Void, ClockErrorResponse> returnsTime(ReturnsTimeRequest request);
}
