package org.svfrms.entity;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.svfrms.enums.Breach;
import org.svfrms.enums.SensorType;
import org.svfrms.port.UsageRecorder;
import org.svfrms.util.BreachUsage;

public class Usage implements UsageRecorder {
    private int totalWaterUsage = 0;
    private int totalEnergyUsage = 0;
    private final ReentrantReadWriteLock usageLock = new ReentrantReadWriteLock();
    private final ReadLock usageReadLock = usageLock.readLock();
    private final WriteLock usageWriteLock = usageLock.writeLock();

    public int getTotalEnergyUsage() {
        usageReadLock.lock();
        try {
            return totalEnergyUsage;
        } finally {
            usageReadLock.unlock();
        }
    }

    public int getTotalWaterUsage() {
        usageReadLock.lock();
        try {
            return totalWaterUsage;
        } finally {
            usageReadLock.unlock();
        }
    }

    public void recordEnergyUsage(int energyUsage) {
        usageWriteLock.lock();
        try {
            totalEnergyUsage += energyUsage;
        } finally {
            usageWriteLock.unlock();
        }
    }

    public void recordWaterUsage(int waterUsage) {
        usageWriteLock.lock();
        try {
            totalWaterUsage += waterUsage;
        } finally {
            usageWriteLock.unlock();
        }
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
