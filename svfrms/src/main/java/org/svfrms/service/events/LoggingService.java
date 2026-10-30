package org.svfrms.service.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoggingService {
    private static List<String> sensorLogs = new ArrayList<>();

    public void addLog(String message) {
        sensorLogs.add(new Date() + " : " + message);
    }

    public List<String> getLogs() {
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
