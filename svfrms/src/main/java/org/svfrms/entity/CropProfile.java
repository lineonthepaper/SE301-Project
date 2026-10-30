package org.svfrms.entity;

import java.util.Map;

import org.svfrms.enums.SensorType;
import org.svfrms.util.EnergyUsage;
import org.svfrms.util.SensorLimits;
import org.svfrms.util.WaterUsage;

public record CropProfile(Map<SensorType, SensorLimits> limits,
        Map<SensorType, EnergyUsage> energyUsageMap,
        Map<SensorType, WaterUsage> waterUsageMap) {
}