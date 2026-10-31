package org.svfrms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.svfrms.entity.Usage;
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
        List<String> chamberList = List.of("CH1", "CH2", "CH3", "CH4");
        List<String> cropList = List.of("Tomato", "Strawberry", "Lettuce", "Chili");

        ReportService reportService = ReportService.getInstance();
        AlertService alertService = AlertService.getInstance();
        LoggingService loggingService = LoggingService.getInstance();
        TrackingService trackingService = TrackingService.getInstance();
        Usage usage = Usage.getInstance();

        ControlQueue controlQueue = new ControlQueue(
                new ControlService(trackingService, loggingService)::applyControl);
        Thread controlQueueThread = new Thread(controlQueue);
        controlQueueThread.start();

        try (ExecutorService exec = Executors.newFixedThreadPool(chamberList.size())) {
            for (int i = 0; i < chamberList.size(); i++) {
                ProcessSensorService processSensorService = new ProcessSensorService(chamberList.get(i),
                        cropList.get(i), controlQueue, loggingService, trackingService, alertService);

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
