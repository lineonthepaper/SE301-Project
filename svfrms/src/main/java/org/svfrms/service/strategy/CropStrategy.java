package org.svfrms.service.strategy;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropLightLevelStrategy;
import org.svfrms.service.strategy.interfaces.HandleCropNutrientLevelStrategy;

public abstract class CropStrategy implements
        HandleCropLightLevelStrategy,
        HandleCropNutrientLevelStrategy {
    private String chamber;

    public CropStrategy(String chamber) {
        this.chamber = chamber;
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
