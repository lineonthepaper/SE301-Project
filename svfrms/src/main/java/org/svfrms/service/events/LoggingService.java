package org.svfrms.service.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.svfrms.port.SensorLog;

public class LoggingService implements SensorLog {
    private final List<String> sensorLogs = new ArrayList<>();

    private final ReentrantReadWriteLock loggingLock = new ReentrantReadWriteLock();
    private final ReadLock loggingReadLock = loggingLock.readLock();
    private final WriteLock loggingWriteLock = loggingLock.writeLock();

    @Override
    public void addLog(String message) {
        loggingWriteLock.lock();
        try {
            sensorLogs.add(new Date() + " : " + message);
        } finally {
            loggingWriteLock.unlock();
        }
    }

    public List<String> getLogs() {
        loggingReadLock.lock();
        try {
            return new ArrayList<>(sensorLogs);
        } finally {
            loggingReadLock.unlock();
        }
    }

    public void printControlLog(String chamber, String message) {
        System.out.println("\t\u001B[32m[" + chamber + "][CONTROL] " + message + "\u001B[0m");
    }

    public void showLogs() {
        loggingReadLock.lock();
        try {
            for (String s : sensorLogs) {
                System.out.println(s);
            }
        } finally {
            loggingReadLock.unlock();
        }
    }
}
