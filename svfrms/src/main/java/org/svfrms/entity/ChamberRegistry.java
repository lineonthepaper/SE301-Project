package org.svfrms.entity;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public final class ChamberRegistry {
    private final Map<String, ChamberState> states = new ConcurrentHashMap<>();
    private final Map<String, ChamberSummary> summaries = new ConcurrentHashMap<>();

    public ChamberState getState(String chamber) {
        return states.computeIfAbsent(chamber, c -> new ChamberState());
    }

    public ChamberSummary getSummary(String chamber) {
        return summaries.computeIfAbsent(chamber, c -> new ChamberSummary());
    }

    public SortedMap<String, ChamberSummary> getSummariesByChamber() {
        return new TreeMap<>(summaries);
    }
}
