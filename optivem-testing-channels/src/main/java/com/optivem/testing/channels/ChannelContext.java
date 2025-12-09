package com.optivem.testing.channels;

/**
 * Thread-local context holder for the current channel being tested.
 * Used by the ChannelExtension to set the channel for each test invocation,
 * and by DriverFactory to determine which driver implementation to create.
 */
public class ChannelContext {

    private static final ThreadLocal<String> channelThreadLocal = new ThreadLocal<>();

    /**
     * Set the current channel for the current thread.
     */
    public static void set(String channel) {
        channelThreadLocal.set(channel);
    }

    /**
     * Get the current channel for the current thread.
     */
    public static String get() {
        return channelThreadLocal.get();
    }

    /**
     * Clear the channel context for the current thread.
     */
    public static void clear() {
        channelThreadLocal.remove();
    }
}

