package org.svfrms.entity;

import java.util.HashMap;

public class ChamberSummary {
    private int alerts = 0;
    private int mitigationActions = 0;
    private String lastAction = "No action";
    private String lastAlert = "None";

    public static HashMap<String, ChamberSummary> chamberSummaries = new HashMap<>();

    public static ChamberSummary getChamberSummary(String chamber) {
        if (!chamberSummaries.containsKey(chamber)) {
            chamberSummaries.put(chamber, new ChamberSummary());
        }
        return chamberSummaries.get(chamber);
    }

    public int getAlerts() {
        return this.alerts;
    }

    public void setAlerts(int alerts) {
        this.alerts = alerts;
    }

    public int getMitigationActions() {
        return this.mitigationActions;
    }

    public void setMitigationActions(int mitigationActions) {
        this.mitigationActions = mitigationActions;
    }

    public String getLastAction() {
        return this.lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getLastAlert() {
        return this.lastAlert;
    }

    public void setLastAlert(String lastAlert) {
        this.lastAlert = lastAlert;
    }
}
