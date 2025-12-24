package com.optivem.eshop.systemtest.core.clock;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Clock {
    Instant now();
    LocalDateTime nowLocalDateTime();
    LocalDate nowLocalDate();
}

