package com.optivem.eshop.systemtest.core.shop.dsl.common;

import com.optivem.eshop.systemtest.core.shop.dsl.common.verifications.SystemErrorFailureVerification;

public interface FailureResult {
    SystemErrorFailureVerification shouldFail();
}
