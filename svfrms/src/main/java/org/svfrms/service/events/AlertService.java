package org.svfrms.service.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.svfrms.port.AlertSink;

public class AlertService implements AlertSink {

    private final List<String> alerts = new ArrayList<>();
    private final List<String> archivedAlerts = new ArrayList<>();

    private final ReentrantReadWriteLock alertLock = new ReentrantReadWriteLock();
    private final ReadLock alertReadLock = alertLock.readLock();
    private final WriteLock alertWriteLock = alertLock.writeLock();

    private final TrackingService trackingService;

    public AlertService(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @Override
    public void addAlert(String chamber, String crop, String message) {
        trackingService.trackAlert(chamber, message);
        alertWriteLock.lock();
        try {
            alerts.add(message);
        } finally {
            alertWriteLock.unlock();
        }
        System.out.println("[" + chamber + "][" + crop + "][ALERT] " + message);
    }

    public List<String> getAlerts() {
        alertReadLock.lock();
        try {
            return new ArrayList<>(alerts);
        } finally {
            alertReadLock.unlock();
        }
    }

    public String archiveAlerts() {
        alertWriteLock.lock();
        try {
            ArrayList<String> toArchive = new ArrayList<>();
            if (!alerts.isEmpty()) {
                toArchive.addAll(alerts);
                alerts.clear();
            }
            if (!toArchive.isEmpty()) {
                archivedAlerts.addAll(toArchive);
                System.out.println("Archiving alerts... " + toArchive.size() + " moved to archive.");
                return "Archived " + toArchive.size() + " alerts";
            }
            System.out.println("Archiving alerts... no active alerts to archive.");
            return "No alerts to archive";
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
