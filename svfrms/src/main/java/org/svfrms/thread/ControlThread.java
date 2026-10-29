package org.svfrms.thread;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.ControlService;

public class ControlThread extends Thread {
    public static volatile boolean controlThreadRunning = true;

    @Override
    public void run() {
        while (true) {
            String request;

            synchronized (ControlRequestService.controlQueue) {
                while (ControlRequestService.controlQueue.isEmpty() && controlThreadRunning) {
                    try {
                        ControlRequestService.controlQueue.wait();
                    } catch (Exception e) {
                    }
                }

                if (ControlRequestService.controlQueue.isEmpty() && !controlThreadRunning) {
                    break;
                }

                request = ControlRequestService.controlQueue.remove(0);
                ControlRequestService.controlQueue.notifyAll();
            }

            String[] parts = request.split("\\|");
            ControlService controlService = new ControlService(parts[0]);
            controlService.applyControl(parts[1]);
        }
    }
}
