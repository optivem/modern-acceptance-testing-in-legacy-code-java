package com.optivem.eshop.systemtest.dsl.port.background;

public interface BackgroundDsl {
    BackgroundDsl shopRunning();

    BackgroundDsl erpRunning();

    BackgroundDsl taxRunning();

    BackgroundDsl clockRunning();
}
