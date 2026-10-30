package org.svfrms;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.svfrms.runnable.ChamberRunnable;
import org.svfrms.runnable.ControlQueue;
import org.svfrms.service.ReportService;
import org.svfrms.service.control.ControlService;
import org.svfrms.service.events.AlertService;

public class SVFRMS {
    public static void main(String[] args) {
        List<String> chamberList = List.of("CH1", "CH2", "CH3", "CH4");
        List<String> cropList = List.of("Tomato", "Strawberry", "Lettuce", "Chili");

        ControlQueue controlQueue = new ControlQueue(new ControlService()::applyControl);
        Thread controlQueueThread = new Thread(controlQueue);
        try (ExecutorService exec = Executors.newFixedThreadPool(chamberList.size() + 1)) {
            ExecutorCompletionService<Void> completed = new ExecutorCompletionService<>(exec);
            try {
                completed.submit(() -> {
                    controlQueueThread.start();
                    return null;
                });
                for (int i = 0; i < chamberList.size(); i++) {
                    ChamberRunnable chamberRunnable = new ChamberRunnable(chamberList.get(i), cropList.get(i),
                            controlQueue);

                    exec.submit(chamberRunnable);
                }

                completed.submit(() -> {
                    controlQueue.shutdown();
                    return null;
                });
                completed.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                exec.shutdownNow();
            }
        }
        try {
            controlQueueThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread genReport = new Thread(new Runnable() {
            @Override
            public void run() {
                ReportService.printDailyReport();
            }
        });

        Thread archiveAlerts = new Thread(new Runnable() {
            @Override
            public void run() {
                AlertService.archiveAlerts();
            }
        });

        archiveAlerts.start();
        genReport.start();

        try {
            genReport.join();
            archiveAlerts.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("System finished sensor processing.");
        ReportService.saveArchiveReport();
    }
}
