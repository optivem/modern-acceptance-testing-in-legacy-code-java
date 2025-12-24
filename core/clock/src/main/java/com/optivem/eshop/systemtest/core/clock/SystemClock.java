package com.optivem.eshop.systemtest.core.clock;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SystemClock implements Clock {

    private final ZoneId zoneId;

    public SystemClock() {
        this(ZoneId.systemDefault());
    }

    public SystemClock(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public Instant now() {
        return Instant.now();
    }

    @Override
    public LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now(zoneId);
    }

    @Override
    public LocalDate nowLocalDate() {
        return LocalDate.now(zoneId);
    }
}

