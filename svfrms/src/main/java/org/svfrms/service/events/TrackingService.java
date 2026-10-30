package org.svfrms.service.events;

import org.svfrms.entity.ChamberSummary;

public class TrackingService {
    public void trackAlert(String chamber, String message) {
        ChamberSummary summary = ChamberSummary.getChamberSummary(chamber);
        summary.setAlerts(summary.getAlerts() + 1);
        summary.setLastAlert(message);
    }

    public void trackAction(String chamber, String message) {
        ChamberSummary summary = ChamberSummary.getChamberSummary(chamber);
        summary.setMitigationActions(summary.getMitigationActions() + 1);
        summary.setLastAction(message);
    }
}
