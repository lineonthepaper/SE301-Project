package org.svfrms.service.events;

import java.util.List;

public interface LoggingInterface {
    public void addLog(String message);

    public List<String> getLogs();
}
