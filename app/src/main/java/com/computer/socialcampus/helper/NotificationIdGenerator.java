package com.computer.socialcampus.helper;

import java.util.concurrent.atomic.AtomicInteger;

public class NotificationIdGenerator {
    private static final AtomicInteger uniqueIdCounter = new AtomicInteger(0);

    public static int getUniqueId() {
        return uniqueIdCounter.incrementAndGet();
    }
}
