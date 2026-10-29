package org.svfrms.service;

import java.util.ArrayList;
import java.util.List;

import org.svfrms.util.constants.ControlConstants;

public class ControlRequestService {
    public static List<String> controlQueue = new ArrayList<>();

    public static void addControlRequest(String chamber, String control) {
        synchronized (controlQueue) {
            while (controlQueue.size() == ControlConstants.controlQueueSize) {
                try {
                    controlQueue.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            controlQueue.add(chamber + "|" + control);
            controlQueue.notifyAll();
        }
    }
}
