package org.svfrms.service;

import org.svfrms.entity.Usage;

public class CycleService {
    public static void irrigationCycle() {
        Usage.recordWaterUsage(100);
    }

    public static void feedingCycle() {
        Usage.recordEnergyUsage(20);
    }
}
