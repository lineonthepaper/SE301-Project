package org.svfrms.util;

public enum Breach {
    HIGH("High"), LOW("Low");

    private final String label;

    Breach(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
