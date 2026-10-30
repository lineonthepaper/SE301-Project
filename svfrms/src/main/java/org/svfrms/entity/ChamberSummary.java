package org.svfrms.entity;

public class ChamberSummary {
    private int alerts = 0;
    private int mitigationActions = 0;
    private String lastAction = "No action";
    private String lastAlert = "None";

    public int getAlerts() {
        return this.alerts;
    }

    public int getMitigationActions() {
        return this.mitigationActions;
    }

    public void recordAlert(String lastAlert) {
        this.alerts++;
        this.lastAlert = lastAlert;
    }

    public void recordMitigationAction(String lastAction) {
        this.mitigationActions++;
        this.lastAction = lastAction;
    }

    public String getLastAction() {
        return this.lastAction;
    }

    public String getLastAlert() {
        return this.lastAlert;
    }
}
