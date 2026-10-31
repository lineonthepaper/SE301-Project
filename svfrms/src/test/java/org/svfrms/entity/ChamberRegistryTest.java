package org.svfrms.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChamberRegistryTest {
    ChamberRegistry chamberRegistry;

    @BeforeEach
    void init() {
        chamberRegistry = new ChamberRegistry();
    }

    @Test
    void verifyConcurrencyWhenGettingChamberState() {
        final int THREAD_COUNT = 4;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);
        List<ChamberState> chamberStateList = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < THREAD_COUNT; i++) {
            exec.submit(() -> {
                chamberStateList.add(chamberRegistry.getState("CH1"));
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < THREAD_COUNT; i++) {
            assertEquals(chamberStateList.get(0), chamberStateList.get(i));
        }
    }

    @Test
    void verifyConcurrencyWhenGettingChamberSummary() {
        final int THREAD_COUNT = 4;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);
        List<ChamberSummary> chamberSummaryList = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < THREAD_COUNT; i++) {
            exec.submit(() -> {
                chamberSummaryList.add(chamberRegistry.getSummary("CH1"));
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < THREAD_COUNT; i++) {
            assertEquals(chamberSummaryList.get(0), chamberSummaryList.get(i));
        }
    }
}
