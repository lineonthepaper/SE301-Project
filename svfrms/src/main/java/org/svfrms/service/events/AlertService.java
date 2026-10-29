package org.svfrms.service.events;

import java.util.ArrayList;
import java.util.List;

public class AlertService implements LoggingInterface {
    private String chamber;
    private String crop;

    private static List<String> alerts = new ArrayList<>();
    private static int totalAlerts = 0;
    private static int totalAlertsGenerated = 0;

    public AlertService(String chamber, String crop) {
        this.chamber = chamber;
        this.crop = crop;
    }

    public void addLog(String message) {
        alerts.add(message);
        totalAlerts++;
        totalAlertsGenerated++;
        System.out.println("[" + chamber + "][" + crop + "][ALERT] " + message);
    }

    public List<String> getLogs() {
        return new ArrayList<>(alerts);
    }

    public void printAlertLog(String message) {
        System.out.println("[" + chamber + "][" + crop + "][ALERT] " + message);
    }

}
