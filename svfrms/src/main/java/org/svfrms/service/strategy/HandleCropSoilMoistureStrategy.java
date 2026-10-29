package org.svfrms.service.strategy;

import org.svfrms.entity.Usage;
import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropSoilMoistureStrategyInterface;

public enum HandleCropSoilMoistureStrategy implements HandleCropSoilMoistureStrategyInterface {
    Tomato {
        @Override
        public String soilMoistureLow(String chamber) {
            String message = chamber + " Tomato Low Soil Moisture";
            Usage.totalWaterUsage += 50;
            ControlRequestService.addControlRequest(chamber, "Water Pump");
            return message;
        }
    }
}
