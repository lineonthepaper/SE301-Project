package org.svfrms.service.strategy;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropNutrientLevelStrategyInterface;

public class HandleCropNutrientLevelStrategy implements HandleCropNutrientLevelStrategyInterface {
    public String nutrientLevelLow(String chamber) {
        String message = chamber + " Low Nutrient Level";
        ControlRequestService.addControlRequest(chamber, "Nutrient Injector");
        return message;
    }
}
