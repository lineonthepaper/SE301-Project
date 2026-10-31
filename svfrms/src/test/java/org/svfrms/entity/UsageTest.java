package org.svfrms.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsageTest {
    Usage usage;

    @BeforeEach
    void init() {
        usage = new Usage();
    }

    @Test
    void verifyConcurrencyWhenRecordingUsage() {
        final int THREAD_COUNT = 4;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);
        int waterUsagePerThread = 50;
        int energyUsagePerThread = 50;

        for (int i = 0; i < THREAD_COUNT; i++) {
            exec.submit(() -> {
                for (int j = 0; j < waterUsagePerThread; j++) {
                    usage.recordWaterUsage(1);
                }

                for (int j = 0; j < energyUsagePerThread; j++) {
                    usage.recordEnergyUsage(1);
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exec.shutdown();

        assertEquals(energyUsagePerThread * THREAD_COUNT, usage.getTotalEnergyUsage());
        assertEquals(waterUsagePerThread * THREAD_COUNT, usage.getTotalWaterUsage());
    }
}