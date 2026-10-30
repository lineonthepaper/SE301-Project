package org.svfrms.util;

public class EnergyUsage implements UsageInterface {
    private final int highBreachUsage;
    private final int lowBreachUsage;

    public EnergyUsage(
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
