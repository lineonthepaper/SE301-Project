package org.svfrms.entity;

public class ChamberSummary {
    private int alerts = 0;
    private int mitigationActions = 0;
    private String lastAction = "No action";
    private String lastAlert = "None";

    public synchronized int getAlerts() {
        return this.alerts;
    }

    public synchronized int getMitigationActions() {
        return this.mitigationActions;
    }

    public synchronized void recordAlert(String lastAlert) {
        this.alerts++;
        this.lastAlert = lastAlert;
    }

    public synchronized void recordMitigationAction(String lastAction) {
        this.mitigationActions++;
        this.lastAction = lastAction;
    }

    public synchronized String getLastAction() {
        return this.lastAction;
    }

    public synchronized String getLastAlert() {
        return this.lastAlert;
    }
}
