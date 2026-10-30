package org.svfrms.service.events;

import java.util.ArrayList;
import java.util.List;

import org.svfrms.service.ReportService;

public class AlertService {

    private static final List<String> alerts = new ArrayList<>();
    private static final List<String> archivedAlerts = new ArrayList<>();

    public synchronized void addAlert(String chamber, String crop, String message) {
        alerts.add(message);
        System.out.println("[" + chamber + "][" + crop + "][ALERT] " + message);
    }

    public synchronized List<String> getAlerts() {
        return new ArrayList<>(alerts);
    }

    public synchronized void archiveAlerts(ReportService reportService) {
        ArrayList<String> toArchive = new ArrayList<>();
        if (!alerts.isEmpty()) {
            toArchive.addAll(alerts);
            alerts.clear();
        }

        if (!toArchive.isEmpty()) {
            archivedAlerts.addAll(toArchive);
            reportService.addReport("Archived " + toArchive.size() + " alerts");
            System.out.println("Archiving alerts... " + toArchive.size() + " moved to archive.");
        } else {
            reportService.addReport("No alerts to archive");
            System.out.println("Archiving alerts... no active alerts to archive.");
        }
    }

    public synchronized List<String> getArchivedAlerts() {
        return new ArrayList<>(archivedAlerts);
    }

    public synchronized int getTotalAlertsGenerated() {
        return archivedAlerts.size() + alerts.size();
    }

    public void showAlerts() {
        for (String a : alerts) {
            System.out.println(a);
        }
    }
}
