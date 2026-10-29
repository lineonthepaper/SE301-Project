package org.svfrms.entity;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public final class ChamberRegistry {
    private static final Map<String, ChamberState> states = new ConcurrentHashMap<>();
    private static final Map<String, ChamberSummary> summaries = new ConcurrentHashMap<>();

    public static ChamberState getState(String chamber) {
        return states.computeIfAbsent(chamber, c -> new ChamberState());
    }

    public static ChamberSummary getSummary(String chamber) {
        return summaries.computeIfAbsent(chamber, c -> new ChamberSummary());
    }

    public static SortedMap<String, ChamberSummary> getSummariesByChamber() {
        return new TreeMap<>(summaries);
    }
}
