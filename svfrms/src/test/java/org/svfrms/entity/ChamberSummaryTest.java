package org.svfrms.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

public class ChamberSummaryTest {
    @Test
    void verifyConcurrencyWhenUpdatingChamberSummary() {
        final int THREAD_COUNT = 4;
        final int UPDATE_COUNT = 50;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);

        ChamberSummary chamberSummary = new ChamberSummary();

        for (int i = 0; i < THREAD_COUNT; i++) {
            exec.submit(() -> {
                for (int j = 0; j < UPDATE_COUNT; j++) {
                    chamberSummary.recordAlert("Alert");
                    chamberSummary.recordMitigationAction("Action");
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals((THREAD_COUNT * UPDATE_COUNT), chamberSummary.getAlerts());
    }
}
