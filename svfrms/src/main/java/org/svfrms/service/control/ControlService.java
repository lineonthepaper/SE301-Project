package org.svfrms.service.control;

import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.ChamberState;
import org.svfrms.request.ControlRequest;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;

public class ControlService {
    private ChamberState state;
    private TrackingService trackingService;
    private LoggingService loggingService;

    public ControlService(String chamber) {
        this.state = ChamberRegistry.getState(chamber);
        this.trackingService = new TrackingService(chamber);
        this.loggingService = new LoggingService(chamber);
    }

    public void applyControl(ControlRequest controlRequest) {
        controlRequest.control().apply(state);
        String result = controlRequest.control().apply(state);
        String activated = controlRequest.control().getLabel() + " Activated";
        trackingService.trackAlert(activated);
        loggingService.printControlLog(activated);
        loggingService.printControlLog(result);
    }

}
