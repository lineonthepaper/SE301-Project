package org.svfrms.service.events;

import org.svfrms.entity.Chamber;
import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.ChamberSummary;

public class TrackingService {
    private final ChamberRegistry chamberRegistry;

    public TrackingService(ChamberRegistry chamberRegistry) {
        this.chamberRegistry = chamberRegistry;
    }

    public void trackAlert(Chamber chamber, String message) {
        ChamberSummary summary = chamberRegistry.getSummary(chamber);
        summary.recordAlert(message);
    }

    public void trackAction(Chamber chamber, String message) {
        ChamberSummary summary = chamberRegistry.getSummary(chamber);
        summary.recordMitigationAction(message);
    }
}
