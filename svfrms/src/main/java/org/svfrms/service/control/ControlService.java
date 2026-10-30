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
        this.trackingService = new TrackingService();
        this.loggingService = new LoggingService();
    }

    public void applyControl(ControlRequest controlRequest) {
        ChamberState state = ChamberRegistry.getState(controlRequest.chamber());
        controlRequest.control().apply(state);
        String result = controlRequest.control().apply(state);
        String activated = controlRequest.control().getLabel() + " Activated";
        trackingService.trackAlert(controlRequest.chamber(), activated);
        loggingService.printControlLog(controlRequest.chamber(), activated);
        loggingService.printControlLog(controlRequest.chamber(), result);
    }

}
