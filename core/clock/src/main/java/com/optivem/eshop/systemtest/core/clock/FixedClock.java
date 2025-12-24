package com.optivem.eshop.systemtest.core.clock;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FixedClock implements Clock {

    private final Instant fixedInstant;
    private final ZoneId zoneId;

    public FixedClock(Instant fixedInstant) {
        this(fixedInstant, ZoneId.systemDefault());
    }

    public FixedClock(Instant fixedInstant, ZoneId zoneId) {
        this.fixedInstant = fixedInstant;
        this.zoneId = zoneId;
    }

    @Override
    public Instant now() {
        return fixedInstant;
    }

    @Override
    public LocalDateTime nowLocalDateTime() {
        return LocalDateTime.ofInstant(fixedInstant, zoneId);
    }

    @Override
    public LocalDate nowLocalDate() {
        return LocalDate.ofInstant(fixedInstant, zoneId);
    }
}

