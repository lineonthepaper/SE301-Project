package org.svfrms.service.control;

import org.svfrms.entity.Chamber;
import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.ChamberState;
import org.svfrms.enums.Control;
import org.svfrms.port.ControlRequester;
import org.svfrms.runnable.ControlQueue;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;

public class ControlService implements ControlRequester {
    private final ChamberRegistry chamberRegistry;
    private final TrackingService trackingService;
    private final LoggingService loggingService;
    private final ControlQueue controlQueue;

    public ControlService(ChamberRegistry chamberRegistry, TrackingService trackingService,
            LoggingService loggingService, ControlQueue controlQueue) {
        this.chamberRegistry = chamberRegistry;
        this.trackingService = trackingService;
        this.loggingService = loggingService;
        this.controlQueue = controlQueue;
    }

    @Override
    public void requestControl(Chamber chamber, Control control) throws InterruptedException {
        controlQueue.addCommand(new ControlRequest(chamber, control, this));
    }

    public void applyControl(Chamber chamber, Control control) {
        ChamberState state = chamberRegistry.getState(chamber);
        String result = control.apply(state);
        String activated = control.getLabel() + " Activated";
        trackingService.trackAction(chamber, activated);
        loggingService.printControlLog(chamber, activated);
        loggingService.printControlLog(chamber, result);
    }

}
