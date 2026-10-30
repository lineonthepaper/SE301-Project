package org.svfrms.service.events;

import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.ChamberSummary;

public class TrackingService {
    private static TrackingService instance = null;

    private TrackingService() {
        // empty constructor
    }

    public static synchronized TrackingService getInstance() {
        if (instance == null) {
            instance = new TrackingService();
        }
        return instance;
    }

    public void trackAlert(String chamber, String message) {
        ChamberSummary summary = ChamberRegistry.getSummary(chamber);
        summary.recordAlert(message);
    }

    public void trackAction(String chamber, String message) {
        ChamberSummary summary = ChamberRegistry.getSummary(chamber);
        summary.recordMitigationAction(message);
    }
}
