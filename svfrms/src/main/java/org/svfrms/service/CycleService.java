package org.svfrms.service;

import org.svfrms.entity.Usage;

public class CycleService {
    public static void irrigationCycle(Usage usage) {
        usage.recordWaterUsage(100);
    }

    public static void feedingCycle(Usage usage) {
        usage.recordEnergyUsage(20);
    }
}
