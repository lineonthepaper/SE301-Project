package org.svfrms.service.strategy;

import org.svfrms.entity.Usage;
import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropTemperatureStrategyInterface;

public enum HandleCropTemperatureStrategy implements HandleCropTemperatureStrategyInterface {
    Tomato {
        @Override
        public String temperatureHigh(String chamber) {
            String message = chamber + " Tomato High Temp";
            Usage.totalEnergyUsage += 10;
            ControlRequestService.addControlRequest(chamber, "Cooling Fan");
            return message;
        }

        @Override
        public String temperatureLow(String chamber) {
            String message = chamber + " Tomato Low Temp";
            Usage.totalEnergyUsage += 15;
            return message;
        }
    },
    Strawberry {
        @Override
        public String temperatureHigh(String chamber) {
            String message = chamber + " Strawberry High Temp";
            Usage.totalEnergyUsage += 10;
            ControlRequestService.addControlRequest(chamber, "Cooling Fan");
            return message;
        }

        @Override
        public String temperatureLow(String chamber) {
            String message = chamber + " Strawberry Low Temp";
            Usage.totalEnergyUsage += 15;
            return message;
        }
    },
    Lettuce {
        @Override
        public String temperatureHigh(String chamber) {
            String message = chamber + " Lettuce High Temp";
            ControlRequestService.addControlRequest(chamber, "Cooling Fan");
            return message;
        }

        @Override
        public String temperatureLow(String chamber) {
            String message = chamber + " Lettuce Low Temp";
            return message;
        }
    },
    Spinach {
        @Override
        public String temperatureHigh(String chamber) {
            String message = chamber + " Spinach High Temp";
            ControlRequestService.addControlRequest(chamber, "Cooling Fan");
            return message;
        }

        @Override
        public String temperatureLow(String chamber) {
            String message = chamber + " Spinach Low Temp";
            return message;
        }
    },
    Chili {
        @Override
        public String temperatureHigh(String chamber) {
            String message = chamber + " Chili High Temp";
            ControlRequestService.addControlRequest(chamber, "Cooling Fan");
            return message;
        }

        @Override
        public String temperatureLow(String chamber) {
            String message = chamber + " Chili Low Temp";
            return message;
        }
    },
    Cucumber {
        @Override
        public String temperatureHigh(String chamber) {
            String message = chamber + " Cucumber High Temp";
            ControlRequestService.addControlRequest(chamber, "Cooling Fan");
            return message;
        }

        @Override
        public String temperatureLow(String chamber) {
            String message = chamber + " Cucumber Low Temp";
            return message;
        }
    };
}
