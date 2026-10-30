package org.svfrms.service.events;

import java.util.ArrayList;
import java.util.List;

import org.svfrms.service.ReportService;

public class AlertService {

    private static List<String> alerts = new ArrayList<>();
    private static List<String> archivedAlerts = new ArrayList<>();

    public synchronized void addAlert(String chamber, String crop, String message) {
        alerts.add(message);
        System.out.println("[" + chamber + "][" + crop + "][ALERT] " + message);
    }

    public synchronized static List<String> getAlerts() {
        return new ArrayList<>(alerts);
    }

    public synchronized static void archiveAlerts() {
        ArrayList<String> toArchive = new ArrayList<>();
        if (!alerts.isEmpty()) {
            toArchive.addAll(alerts);
            alerts.clear();
        }

        if (!toArchive.isEmpty()) {
            archivedAlerts.addAll(toArchive);
            ReportService.addReport("Archived " + toArchive.size() + " alerts");
            System.out.println("Archiving alerts... " + toArchive.size() + " moved to archive.");
        } else {
            ReportService.addReport("No alerts to archive");
            System.out.println("Archiving alerts... no active alerts to archive.");
        }
    }

    public synchronized static List<String> getArchivedAlerts() {
        return new ArrayList<>(archivedAlerts);
    }

    public synchronized static int getTotalAlertsGenerated() {
        return archivedAlerts.size() + alerts.size();
    }

    public static void showAlerts() {
        for (String a : alerts) {
            System.out.println(a);
        }
    }
}
