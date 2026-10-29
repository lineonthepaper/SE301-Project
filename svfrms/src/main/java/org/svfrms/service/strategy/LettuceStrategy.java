package org.svfrms.service.strategy;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropTemperatureStrategy;

public class LettuceStrategy extends CropStrategy
        implements HandleCropTemperatureStrategy {
    private String chamber;

    public LettuceStrategy(String chamber) {
        super(chamber);
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
}
