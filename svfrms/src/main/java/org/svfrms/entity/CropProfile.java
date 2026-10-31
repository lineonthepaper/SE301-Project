package org.svfrms.entity;

import java.util.Map;

import org.svfrms.enums.SensorType;
import org.svfrms.util.BreachUsage;
import org.svfrms.util.SensorLimits;

public record CropProfile(Map<SensorType, SensorLimits> limits,
        Map<SensorType, BreachUsage> energyUsageMap,
        Map<SensorType, BreachUsage> waterUsageMap) {
}