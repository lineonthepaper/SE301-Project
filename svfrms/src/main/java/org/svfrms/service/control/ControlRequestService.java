package org.svfrms.service.control;

import java.util.ArrayList;
import java.util.List;

import org.svfrms.enums.Control;
import org.svfrms.request.ControlRequest;
import org.svfrms.util.constants.ControlConstants;

public class ControlRequestService {
    public static List<ControlRequest> controlQueue = new ArrayList<>();

    public static void addControlRequest(String chamber, String control) {
        synchronized (controlQueue) {
            while (controlQueue.size() == ControlConstants.controlQueueSize) {
                try {
                    controlQueue.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            controlQueue.add(
                    new ControlRequest(chamber, Control.valueOf(control.replace(" ", "_").toUpperCase())));
            controlQueue.notifyAll();
        }
    }
}
