package org.svfrms.entity;

import java.util.HashMap;
import java.util.Map;

import org.svfrms.enums.SensorType;
import org.svfrms.util.EnergyUsage;
import org.svfrms.util.SensorLimits;
import org.svfrms.util.WaterUsage;

// public class CropProfile {
//     private Map<SensorType, SensorLimits> limits;
//     private Map<SensorType, EnergyUsage> energyUsageMap;
//     private Map<SensorType, WaterUsage> waterUsageMap;

//     public CropProfile(
//             Map<SensorType, SensorLimits> limits,
//             Map<SensorType, EnergyUsage> energyUsageMap,
//             Map<SensorType, WaterUsage> waterUsageMap) {
//         this.limits = limits;
//         this.energyUsageMap = energyUsageMap;
//         this.waterUsageMap = waterUsageMap;
//     }

//     public Map<SensorType, SensorLimits> getLimits() {
//         return new HashMap<SensorType, SensorLimits>(limits);
//     }

//     public Map<SensorType, EnergyUsage> getEnergyUsage() {
//         return new HashMap<SensorType, EnergyUsage>(energyUsageMap);
//     }

//     public Map<SensorType, WaterUsage> getWaterUsage() {
//         return new HashMap<SensorType, WaterUsage>(waterUsageMap);
//     }
// }

public record CropProfile(Map<SensorType, SensorLimits> limits,
        Map<SensorType, EnergyUsage> energyUsageMap,
        Map<SensorType, WaterUsage> waterUsageMap) {
}