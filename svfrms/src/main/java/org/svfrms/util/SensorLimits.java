package org.svfrms.util;

public class SensorLimits {
    private final int high;
    private final int low;

    public SensorLimits(int high, int low) {
        this.high = high;
        this.low = low;
    }

    public SensorLimits(int low) {
        this.low = low;
        this.high = Integer.MAX_VALUE;
    }

    public int getHigh() {
        return this.high;
    }

    public int getLow() {
        return this.low;
    }
}
