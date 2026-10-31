package org.svfrms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ReportService reportService = new ReportService(chamberRegistry);
        LoggingService loggingService = new LoggingService();
        TrackingService trackingService = new TrackingService(chamberRegistry);
        AlertService alertService = new AlertService(trackingService);
        Usage usage = new Usage();

        ControlQueue controlQueue = new ControlQueue();
        ControlService controlService = new ControlService(chamberRegistry, trackingService, loggingService,
                controlQueue);
        Thread controlQueueThread = new Thread(controlQueue);
        controlQueueThread.start();

        try (ExecutorService exec = Executors.newFixedThreadPool(chambers.size())) {
            for (Chamber chamber : chambers) {
                ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                        chamberRegistry.getState(chamber.name()), controlService, loggingService, alertService, usage);

                exec.execute(new ChamberRunnable(processSensorService, usage));
            }
        }

        try {
            controlQueue.shutdown();
            controlQueueThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread genReport = new Thread(new Runnable() {
            @Override
            public void run() {
                reportService.printDailyReport(alertService, usage);
            }
        });

        Thread archiveAlerts = new Thread(new Runnable() {
            @Override
            public void run() {
                alertService.archiveAlerts(reportService);
            }
        });

        archiveAlerts.start();

        try {
            archiveAlerts.join();
            genReport.start();
            genReport.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("System finished sensor processing.");
        reportService.saveArchiveReport(alertService, usage);
    }
}
