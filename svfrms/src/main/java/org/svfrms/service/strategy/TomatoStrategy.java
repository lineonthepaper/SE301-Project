package org.svfrms.service.strategy;

import org.svfrms.entity.Usage;
import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropHumidityStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropLightLevelStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropNutrientLevelStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropSoilMoistureStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropTemperatureStrategy;

public class TomatoStrategy
        implements HandleCropTemperatureStrategy,
        HandleCropHumidityStrategy,
        HandleCropSoilMoistureStrategy,
        HandleCropLightLevelStrategy,
        HandleCropNutrientLevelStrategy {

    private String chamber;

    public TomatoStrategy(String chamber) {
        this.chamber = chamber;
    }

    @Override
    public String temperatureHigh() {
        String message = chamber + " Tomato High Temp";
        Usage.totalEnergyUsage += 10;
        ControlRequestService.addControlRequest(chamber, "Cooling Fan");
        return message;
    }

    @Override
    public String temperatureLow() {
        String message = chamber + " Tomato Low Temp";
        Usage.totalEnergyUsage += 15;
        return message;
    }

    @Override
    public String humidityHigh() {
        String message = chamber + " Tomato High Humidity";
        ControlRequestService.addControlRequest(chamber, "Dehumidifier");
        return message;
    }

    @Override
    public String humidityLow() {
        String message = chamber + " Tomato Low Humidity";
        return message;
    }

    @Override
    public String soilMoistureLow() {
        String message = chamber + " Tomato Low Soil Moisture";
        Usage.totalWaterUsage += 50;
        ControlRequestService.addControlRequest(chamber, "Water Pump");
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
