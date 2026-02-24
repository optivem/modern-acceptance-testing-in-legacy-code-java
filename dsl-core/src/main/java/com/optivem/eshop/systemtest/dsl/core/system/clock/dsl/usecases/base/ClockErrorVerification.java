package com.optivem.eshop.systemtest.dsl.core.system.clock.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.clock.dtos.error.ClockErrorResponse;
import com.optivem.eshop.systemtest.dsl.core.system.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

public class ClockErrorVerification extends ResponseVerification<ClockErrorResponse> {
    public ClockErrorVerification(ClockErrorResponse clockErrorResponse, UseCaseContext useCaseContext) {
        super(clockErrorResponse, useCaseContext);
    }
}
