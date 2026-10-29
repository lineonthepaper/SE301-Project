package org.svfrms.util;

public class SensorLimits {
    private int high;
    private int low;

    public SensorLimits(int high, int low) {
        this.high = high;
        this.low = low;
    }

    public SensorLimits(int low) {
        this.low = low;
    }

    public int getHigh() {
        return this.high;
    }

    public int getLow() {
        return this.low;
    }
}
