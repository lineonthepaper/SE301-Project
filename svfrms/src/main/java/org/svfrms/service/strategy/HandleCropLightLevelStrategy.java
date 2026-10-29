package org.svfrms.service.strategy;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropLightLevelStrategyInterface;

public class HandleCropLightLevelStrategy implements HandleCropLightLevelStrategyInterface {
    public String lightLevelLow(String chamber) {
        String message = chamber + " Low Light Level";
        ControlRequestService.addControlRequest(chamber, "Grow Light");
        return message;
    }
}
