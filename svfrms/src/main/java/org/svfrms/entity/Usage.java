package org.svfrms.entity;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Usage {
    private volatile static int totalWaterUsage = 0;
    private volatile static int totalEnergyUsage = 0;
    private static ReentrantReadWriteLock usageLock = new ReentrantReadWriteLock();
    private static ReadLock usageReadLock = usageLock.readLock();
    private static WriteLock usageWriteLock = usageLock.writeLock();

    public static int getTotalEnergyUsage() {
        usageReadLock.lock();
        try {
            return totalEnergyUsage;
        } finally {
            usageReadLock.unlock();
        }
    }

    public static int getTotalWaterUsage() {
        usageReadLock.lock();
        try {
            return totalWaterUsage;
        } finally {
            usageReadLock.unlock();
        }
    }

    public static void recordEnergyUsage(int energyUsage) {
        usageWriteLock.lock();
        try {
            totalEnergyUsage += energyUsage;
        } finally {
            usageWriteLock.lock();
        }
    }

    public static void recordWaterUsage(int waterUsage) {
        usageWriteLock.lock();
        try {
            totalWaterUsage += waterUsage;
        } finally {
            usageWriteLock.lock();
        }
    }
}
