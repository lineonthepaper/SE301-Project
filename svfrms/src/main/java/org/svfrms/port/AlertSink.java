package org.svfrms.port;

public interface AlertSink {
    void addAlert(String chamber, String crop, String message);
}
