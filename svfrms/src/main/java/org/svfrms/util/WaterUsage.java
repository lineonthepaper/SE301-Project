package org.svfrms.util;

public class WaterUsage implements UsageInterface {
    private int highBreachUsage;
    private int lowBreachUsage;

    public WaterUsage(
            int highBreachUsage,
            int lowBreachUsage) {
        this.highBreachUsage = highBreachUsage;
        this.lowBreachUsage = lowBreachUsage;
    }

    public int getHighBreachUsage() {
        return this.highBreachUsage;
    }

    public int getLowBreachUsage() {
        return this.lowBreachUsage;
    }
}
