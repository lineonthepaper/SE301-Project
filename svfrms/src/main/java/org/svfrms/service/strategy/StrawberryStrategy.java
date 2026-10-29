package org.svfrms.service.strategy;

import org.svfrms.entity.Usage;
import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropHumidityStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropLightLevelStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropNutrientLevelStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropTemperatureStrategy;

public class StrawberryStrategy implements HandleCropTemperatureStrategy,
        HandleCropHumidityStrategy,
        HandleCropLightLevelStrategy,
        HandleCropNutrientLevelStrategy {
    private String chamber;

    public StrawberryStrategy(String chamber) {
        this.chamber = chamber;
    }

    @Override
    public String temperatureHigh() {
        String message = chamber + " Strawberry High Temp";
        Usage.totalEnergyUsage += 10;
        ControlRequestService.addControlRequest(chamber, "Cooling Fan");
        return message;
    }

    @Override
    public String temperatureLow() {
        String message = chamber + " Strawberry Low Temp";
        Usage.totalEnergyUsage += 15;
        return message;
    }

    @Override
    public String humidityHigh() {
        String message = chamber + " Strawberry High Humidity";
        ControlRequestService.addControlRequest(chamber, "Dehumidifier");
        return message;
    }

    @Override
    public String humidityLow() {
        String message = chamber + " Strawberry Low Humidity";
        Usage.totalWaterUsage += 25;
        ControlRequestService.addControlRequest(chamber, "Humidifier");
        return message;
    }

    @Override
    public String lightLevelLow() {
        String message = chamber + " Low Light Level";
        ControlRequestService.addControlRequest(chamber, "Grow Light");
        return message;
    }

    @Override
    public String nutrientLevelLow() {
        String message = chamber + " Low Nutrient Level";
        ControlRequestService.addControlRequest(chamber, "Nutrient Injector");
        return message;
    }
}
