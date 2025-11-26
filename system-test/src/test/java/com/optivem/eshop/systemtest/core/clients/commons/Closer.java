package com.optivem.eshop.systemtest.core.clients.commons;

public class Closer {
    public static void close(AutoCloseable client) {
        if(client != null) {
            try {
                client.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
