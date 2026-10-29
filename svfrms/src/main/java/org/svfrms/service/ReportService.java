package org.svfrms.service;

import java.util.ArrayList;
import java.util.List;

import org.svfrms.entity.Usage;
import org.svfrms.service.events.AlertService;

public class ReportService {
    private static List<String> reports = new ArrayList<>();

    public static void printDailyReport() {
        System.out.println("===== DAILY REPORT =====");
        System.out.println("Generated Alerts: " + AlertService.getTotalAlertsGenerated());
        System.out.println("Active Alerts: " + AlertService.getTotalAlerts());
        System.out.println("Water Usage: " + Usage.totalWaterUsage + " L");
        System.out.println("Energy Usage: " + Usage.totalEnergyUsage + " kWh");
        reports.add("Daily Report");
    }
}
