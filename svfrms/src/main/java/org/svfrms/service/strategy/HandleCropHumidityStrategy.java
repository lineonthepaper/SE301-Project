package org.svfrms.service.strategy;

import org.svfrms.entity.Usage;
import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropHumidityStrategyInterface;

public enum HandleCropHumidityStrategy implements HandleCropHumidityStrategyInterface {
    Tomato {
        @Override
        public String humidityHigh(String chamber) {
            String message = chamber + " Tomato High Humidity";
            ControlRequestService.addControlRequest(chamber, "Dehumidifier");
            return message;
        }

        @Override
        public String humidityLow(String chamber) {
            String message = chamber + " Tomato Low Humidity";
            return message;
        }
    },
    Strawberry {
        @Override
        public String humidityHigh(String chamber) {
            String message = chamber + " Strawberry High Humidity";
            ControlRequestService.addControlRequest(chamber, "Dehumidifier");
            return message;
        }

        @Override
        public String humidityLow(String chamber) {
            String message = chamber + " Strawberry Low Humidity";
            Usage.totalWaterUsage += 25;
            ControlRequestService.addControlRequest(chamber, "Humidifier");
            return message;
        }
    }
}
