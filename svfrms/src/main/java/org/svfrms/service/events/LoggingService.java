package org.svfrms.service.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoggingService {
    private static final List<String> sensorLogs = new ArrayList<>();

    private static LoggingService instance;

    private LoggingService() {
        // empty constructor
    }

    public static synchronized LoggingService getInstance() {
        if (instance == null) {
            instance = new LoggingService();
        }
        return instance;
    }

    public synchronized void addLog(String message) {
        sensorLogs.add(new Date() + " : " + message);
    }

    public synchronized List<String> getLogs() {
        return new ArrayList<>(sensorLogs);
    }

    public void printControlLog(String chamber, String message) {
        System.out.println("\t\u001B[32m[" + chamber + "][CONTROL] " + message + "\u001B[0m");
    }

    public static void showLogs() {
        for (String s : sensorLogs) {
            System.out.println(s);
        }
    }
}
