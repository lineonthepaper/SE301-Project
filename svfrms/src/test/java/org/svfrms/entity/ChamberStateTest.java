package org.svfrms.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.svfrms.enums.SensorType;

public class ChamberStateTest {

    @Test
    void verifyConcurrencyWhenUpdatingChamberState() {
        final int THREAD_COUNT = 4;
        final int UPDATE_COUNT = 50;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);

        ChamberState chamberState = new ChamberState();
        double humidity = chamberState.get(SensorType.HUMIDITY);

        for (int i = 0; i < THREAD_COUNT; i++) {
            exec.submit(() -> {
                for (int j = 0; j < UPDATE_COUNT; j++) {
                    chamberState.update(SensorType.HUMIDITY, h -> h + 10);
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(humidity + (10 * THREAD_COUNT * UPDATE_COUNT), chamberState.get(SensorType.HUMIDITY));
    }
}
