package org.svfrms.thread;

import org.svfrms.request.ControlRequest;
import org.svfrms.service.control.ControlRequestService;
import org.svfrms.service.control.ControlService;

public class ControlThread extends Thread {
    public static volatile boolean controlThreadRunning = true;

    @Override
    public void run() {
        while (true) {
            ControlRequest request;

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

            ControlService controlService = new ControlService(request.chamber());
            controlService.applyControl(request);
        }
    }
}
