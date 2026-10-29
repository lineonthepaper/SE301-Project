package org.svfrms;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.ReportService;
import org.svfrms.service.events.AlertService;
import org.svfrms.thread.ChamberThread;
import org.svfrms.thread.ControlThread;

public class SVFRMS {
    public static void main(String[] args) {
        Thread activateControlThread = new ControlThread();
        Thread t1 = new ChamberThread("CH1", "Tomato");
        Thread t2 = new ChamberThread("CH2", "Strawberry");
        Thread t3 = new ChamberThread("CH3", "Lettuce");
        Thread t4 = new ChamberThread("CH4", "Chili");

        activateControlThread.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            synchronized (ControlRequestService.controlQueue) {
                ControlThread.controlThreadRunning = false;
                ControlRequestService.controlQueue.notifyAll();
            }

            activateControlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread genReport = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("printing report");
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
