package org.svfrms.entity;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.svfrms.enums.Breach;
import org.svfrms.enums.SensorType;
import org.svfrms.util.EnergyUsage;
import org.svfrms.util.WaterUsage;

public class Usage {
    private volatile static int totalWaterUsage = 0;
    private volatile static int totalEnergyUsage = 0;
    private static final ReentrantReadWriteLock usageLock = new ReentrantReadWriteLock();
    private static final ReadLock usageReadLock = usageLock.readLock();
    private static final WriteLock usageWriteLock = usageLock.writeLock();

    private static Usage instance = null;

    private Usage() {
        // empty constructor
    }

    public static synchronized Usage getInstance() {
        if (instance == null) {
            instance = new Usage();
        }
        return instance;
    }

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

    public void recordBreachUsage(CropProfile cropProfile, SensorType sensor, Breach breach) {
        EnergyUsage energyUsage = cropProfile.energyUsageMap().get(sensor);
        if (energyUsage != null) {
            recordEnergyUsage(breach == Breach.HIGH ? energyUsage.getHighBreachUsage()
                    : energyUsage.getLowBreachUsage());
        }

        WaterUsage waterUsage = cropProfile.waterUsageMap().get(sensor);
        if (waterUsage != null) {
            recordWaterUsage(breach == Breach.HIGH ? waterUsage.getHighBreachUsage()
                    : waterUsage.getLowBreachUsage());
        }
    }
}
