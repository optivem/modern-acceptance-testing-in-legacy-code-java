package com.optivem.eshop.systemtest.core.clock.driver;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.ReturnsTimeRequest;

public interface ClockDriver extends AutoCloseable {
    ClockResult<Void> goToClock();
    ClockResult<GetTimeResponse> getTime();
    ClockResult<Void> returnsTime(ReturnsTimeRequest request);
}
