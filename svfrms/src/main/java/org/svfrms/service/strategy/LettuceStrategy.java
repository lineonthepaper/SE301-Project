package org.svfrms.service.strategy;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropLightLevelStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropNutrientLevelStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropTemperatureStrategy;

public class LettuceStrategy
        implements HandleCropTemperatureStrategy,
        HandleCropLightLevelStrategy,
        HandleCropNutrientLevelStrategy {
    private String chamber;

    public LettuceStrategy(String chamber) {
        this.chamber = chamber;
    }

    @Override
    public String temperatureHigh() {
        String message = chamber + " Lettuce High Temp";
        ControlRequestService.addControlRequest(chamber, "Cooling Fan");
        return message;
    }

    @Override
    public String temperatureLow() {
        String message = chamber + " Lettuce Low Temp";
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
