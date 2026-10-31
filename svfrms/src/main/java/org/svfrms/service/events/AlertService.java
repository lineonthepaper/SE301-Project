package org.svfrms.service.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.svfrms.service.ReportService;

public class AlertService {

    private static final List<String> alerts = new ArrayList<>();
    private static final List<String> archivedAlerts = new ArrayList<>();

    private static final ReentrantReadWriteLock alertLock = new ReentrantReadWriteLock();
    private static final ReadLock alertReadLock = alertLock.readLock();
    private static final WriteLock alertWriteLock = alertLock.writeLock();

    private static AlertService instance = null;

    private AlertService() {
        // empty constructor
    }

    public static synchronized AlertService getInstance() {
        if (instance == null) {
            instance = new AlertService();
        }
        return instance;
    }

    public void addAlert(String chamber, String crop, String message) {
        alertWriteLock.lock();
        try {
            alerts.add(message);
        } finally {
            alertWriteLock.unlock();
        }
        System.out.println("[" + chamber + "][" + crop + "][ALERT] " + message);
    }

    public synchronized List<String> getAlerts() {
        alertReadLock.lock();
        try {
            return new ArrayList<>(alerts);
        } finally {
            alertReadLock.unlock();
        }
    }

    public synchronized void archiveAlerts(ReportService reportService) {
        alertWriteLock.lock();
        try {
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
        } finally {
            alertWriteLock.unlock();
        }

    }

    public List<String> getArchivedAlerts() {
        alertReadLock.lock();
        try {
            return new ArrayList<>(archivedAlerts);
        } finally {
            alertReadLock.unlock();
        }
    }

    public int getTotalAlertsGenerated() {
        alertReadLock.lock();
        try {
            return archivedAlerts.size() + alerts.size();
        } finally {
            alertReadLock.unlock();
        }
    }

    public void showAlerts() {
        alertReadLock.lock();
        try {
            for (String a : alerts) {
                System.out.println(a);
            }
        } finally {
            alertReadLock.unlock();
        }
    }
}
