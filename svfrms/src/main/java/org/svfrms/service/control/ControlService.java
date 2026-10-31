package org.svfrms.service.control;

import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.ChamberState;
import org.svfrms.enums.Control;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;

public class ControlService {
    private final TrackingService trackingService;
    private final LoggingService loggingService;

    public ControlService(TrackingService trackingService, LoggingService loggingService) {
        this.trackingService = trackingService;
        this.loggingService = loggingService;
    }

    public void applyControl(String chamber, Control control) {
        ChamberState state = ChamberRegistry.getState(chamber);
        String result = control.apply(state);
        String activated = control.getLabel() + " Activated";
        trackingService.trackAction(chamber, activated);
        loggingService.printControlLog(chamber, activated);
        loggingService.printControlLog(chamber, result);
    }

}
