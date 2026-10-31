package org.svfrms.entity;

import java.util.concurrent.atomic.AtomicInteger;

import org.svfrms.enums.Breach;
import org.svfrms.enums.SensorType;
import org.svfrms.port.UsageRecorder;
import org.svfrms.util.BreachUsage;

public class Usage implements UsageRecorder {
    private final AtomicInteger totalWaterUsage = new AtomicInteger();
    private final AtomicInteger totalEnergyUsage = new AtomicInteger();

    public int getTotalEnergyUsage() {
        return totalEnergyUsage.get();
    }

    public int getTotalWaterUsage() {
        return totalWaterUsage.get();
    }

    public void recordEnergyUsage(int energyUsage) {
        totalEnergyUsage.addAndGet(energyUsage);
    }

    public void recordWaterUsage(int waterUsage) {
        totalWaterUsage.addAndGet(waterUsage);
    }

    @Override
    public void recordBreachUsage(CropProfile cropProfile, SensorType sensor, Breach breach) {
        BreachUsage energyUsage = cropProfile.energyUsageMap().get(sensor);
        if (energyUsage != null) {
            recordEnergyUsage(energyUsage.amountFor(breach));
        }

        BreachUsage waterUsage = cropProfile.waterUsageMap().get(sensor);
        if (waterUsage != null) {
            recordWaterUsage(waterUsage.amountFor(breach));
        }
    }
}
