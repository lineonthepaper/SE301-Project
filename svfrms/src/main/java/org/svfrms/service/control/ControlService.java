package org.svfrms.service.control;

import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.ChamberState;
import org.svfrms.request.ControlRequest;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;

public class ControlService {
    private final TrackingService trackingService;
    private final LoggingService loggingService;

    public ControlService() {
        this.trackingService = TrackingService.getInstance();
        this.loggingService = LoggingService.getInstance();
    }

    public void applyControl(ControlRequest controlRequest) {
        ChamberState state = ChamberRegistry.getState(controlRequest.chamber());
        String result = controlRequest.control().apply(state);
        String activated = controlRequest.control().getLabel() + " Activated";
        trackingService.trackAction(controlRequest.chamber(), activated);
        loggingService.printControlLog(controlRequest.chamber(), activated);
        loggingService.printControlLog(controlRequest.chamber(), result);
    }

}
