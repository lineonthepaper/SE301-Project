package org.svfrms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.svfrms.entity.Chamber;
import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.Usage;
import org.svfrms.enums.Crop;
import org.svfrms.runnable.ChamberRunnable;
import org.svfrms.runnable.ControlQueue;
import org.svfrms.service.ProcessSensorService;
import org.svfrms.service.ReportService;
import org.svfrms.service.control.ControlService;
import org.svfrms.service.events.AlertService;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;

public class SVFRMS {
    public static void main(String[] args) {
        List<Chamber> chambers = List.of(
                new Chamber("CH1", Crop.TOMATO),
                new Chamber("CH2", Crop.STRAWBERRY),
                new Chamber("CH3", Crop.LETTUCE),
                new Chamber("CH4", Crop.CHILI),
                new Chamber("CH5", Crop.KALE));

        ChamberRegistry chamberRegistry = new ChamberRegistry();
        Usage usage = new Usage();
        ReportService reportService = new ReportService(chamberRegistry, usage);
        LoggingService loggingService = new LoggingService();
        TrackingService trackingService = new TrackingService(chamberRegistry);
        AlertService alertService = new AlertService(trackingService);
        Random random = new Random();

        ControlQueue controlQueue = new ControlQueue();
        ControlService controlService = new ControlService(chamberRegistry, trackingService, loggingService,
                controlQueue);
        Thread controlQueueThread = new Thread(controlQueue);
        controlQueueThread.start();
        List<Future<?>> futures = new ArrayList<>();

        try (ExecutorService exec = Executors.newFixedThreadPool(chambers.size())) {
            for (Chamber chamber : chambers) {
                ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                        chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);
                futures.add(exec.submit(
                        new ChamberRunnable(processSensorService, usage, random)));
            }
            try {
                for (Future<?> future : futures) {
                    future.get();
                }
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted before completing its task.");
                System.exit(1);
            } catch (ExecutionException e) {
                System.out.println("There was an error executing this thread");
                System.exit(2);
            }
        }

        try {
            controlQueue.shutdown();
            controlQueueThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reportService.addReport(alertService.archiveAlerts());
        reportService.printDailyReport(alertService);

        System.out.println("System finished sensor processing.");
        reportService.saveArchiveReport(alertService);
    }
}
