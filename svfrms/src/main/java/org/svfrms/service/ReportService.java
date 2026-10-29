package org.svfrms.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.svfrms.entity.ChamberSummary;
import org.svfrms.entity.Usage;
import org.svfrms.service.events.AlertService;

public class ReportService {
    private static List<String> reports = new ArrayList<>();
    private static final Path ARCHIVE_DIR = Paths.get("archive");
    private static final Path REPORT_FILE = ARCHIVE_DIR.resolve("daily_report.txt");

    public static void printDailyReport() {
        System.out.println("===== DAILY REPORT =====");
        System.out.println("Generated Alerts: " + AlertService.getTotalAlertsGenerated());
        System.out.println("Active Alerts: " + AlertService.getTotalAlerts());
        System.out.println("Water Usage: " + Usage.totalWaterUsage + " L");
        System.out.println("Energy Usage: " + Usage.totalEnergyUsage + " kWh");
        reports.add("Daily Report");
    }

    public static void addReport(String report) {
        reports.add(report);
    }

    public static void saveArchiveReport() {
        try {
            Files.createDirectories(ARCHIVE_DIR);

            List<String> archivedAlerts = AlertService.getArchivedAlerts();
            HashMap<String, ChamberSummary> chamberSummaries = ChamberSummary.getChamberSummaries();

            ArrayList<String> reportLines = new ArrayList<String>();
            reportLines.add("===== DAILY REPORT =====");
            reportLines.add("Timestamp: " + new Date());
            reportLines.add("Generated Alerts: " + AlertService.getTotalAlertsGenerated());
            reportLines.add("Active Alerts: " + AlertService.getTotalAlerts());
            reportLines.add("Water Usage: " + Usage.totalWaterUsage + " L");
            reportLines.add("Energy Usage: " + Usage.totalEnergyUsage + " kWh");
            reportLines.add("Archived alerts: " + archivedAlerts.size());
            reportLines.add("---");
            reportLines.addAll(reports);

            if (chamberSummaries.size() > 0) {
                reportLines.add("--- chamber wise summary ---");
                TreeSet<String> sortedChambers = new TreeSet<String>(chamberSummaries.keySet());
                for (String chamber : sortedChambers) {
                    ChamberSummary summary = chamberSummaries.get(chamber);
                    reportLines.add(chamber + " -> Alerts=" + summary.getAlerts() + ", Actions="
                            + summary.getMitigationActions()
                            + ", Last Action=" + summary.getLastAction() + ", Last Alert=" + summary.getLastAlert());
                }
            }

            if (archivedAlerts.size() > 0) {
                reportLines.add("--- archived alerts ---");
                reportLines.addAll(archivedAlerts);
            }

            Files.write(REPORT_FILE, reportLines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);

            System.out.println("Archive report saved to: " + REPORT_FILE.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Could not save archive report: " + e.getMessage());
        }
    }
}
