package org.svfrms.service.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svfrms.entity.Chamber;
import org.svfrms.entity.ChamberRegistry;
import org.svfrms.enums.Crop;

public class AlertServiceTest {
    ChamberRegistry chamberRegistry;
    TrackingService trackingService;
    AlertService alertService;

    @BeforeEach
    void init() {
        chamberRegistry = new ChamberRegistry();
        trackingService = new TrackingService(chamberRegistry);
        alertService = new AlertService(trackingService);
    }

    @Test
    void verifyConcurrencyWhenAddingAlerts() {
        final int THREAD_COUNT = 4;
        final int UPDATE_COUNT = 50;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);
        Chamber chamber = new Chamber("CH1", Crop.CUCUMBER);

        for (int i = 0; i < THREAD_COUNT; i++) {
            exec.submit(() -> {
                for (int j = 0; j < UPDATE_COUNT; j++) {
                    alertService.addAlert(chamber, "Alert test");
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(THREAD_COUNT * UPDATE_COUNT, alertService.getAlerts().size());
    }
}
