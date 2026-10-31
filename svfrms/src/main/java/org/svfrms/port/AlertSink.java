package org.svfrms.port;

import org.svfrms.entity.Chamber;

public interface AlertSink {
    void addAlert(Chamber chamber, String message);
}
