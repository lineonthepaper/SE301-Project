package org.svfrms.service.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoggingServiceTest {
    LoggingService loggingService;

    @BeforeEach
    void init() {
        loggingService = new LoggingService();
    }

    @Test
    void verifyConcurrencyWhenAddingLogs() {
        final int THREAD_COUNT = 4;
        final int UPDATE_COUNT = 50;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            exec.submit(() -> {
                for (int j = 0; j < UPDATE_COUNT; j++) {
                    loggingService.addLog("Log");
                }
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(THREAD_COUNT * UPDATE_COUNT, loggingService.getLogs().size());
    }

    @Test
    void editingReturnedLogsDoesNotAlterStoredLogs() {
        loggingService.addLog("Log 1");
        loggingService.addLog("Log 2");

        List<String> returnedLogs = loggingService.getLogs();
        returnedLogs.clear();

        assertEquals(2, loggingService.getLogs().size());
    }
}
