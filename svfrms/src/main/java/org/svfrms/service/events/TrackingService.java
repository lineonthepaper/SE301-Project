package org.svfrms.service.events;

import org.svfrms.entity.ChamberSummary;

public class TrackingService {
    private String chamber;

    public TrackingService(String chamber) {
        this.chamber = chamber;
    }

    public void trackAlert(String message) {
        ChamberSummary summary = ChamberSummary.getChamberSummary(chamber);
        summary.setAlerts(summary.getAlerts() + 1);
        summary.setLastAlert(message);
        ;
    }

    public void trackAction(String message) {
        ChamberSummary summary = ChamberSummary.getChamberSummary(chamber);
        summary.setMitigationActions(summary.getMitigationActions() + 1);
        summary.setLastAction(message);
    }
}
