package org.svfrms.service;

import java.util.Map;

import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.ChamberState;
import org.svfrms.entity.CropProfile;
import org.svfrms.entity.Usage;
import org.svfrms.enums.Breach;
import org.svfrms.enums.Control;
import org.svfrms.enums.Crop;
import org.svfrms.enums.SensorType;
import org.svfrms.request.ControlRequest;
import org.svfrms.runnable.ControlQueue;
import org.svfrms.service.events.AlertService;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;
import org.svfrms.util.SensorLimits;

public class ProcessSensorService {
    private final String chamber;
    private final String crop;
    private final ControlQueue controlQueue;

    private final ChamberState state;
    private final LoggingService loggingService;
    private final TrackingService trackingService;
    private final AlertService alertService;

    public ProcessSensorService(String chamber, String crop,
            ControlQueue controlQueue) {
        this.chamber = chamber;
        this.crop = crop;
        this.controlQueue = controlQueue;

        this.state = ChamberRegistry.getState(chamber);
        this.loggingService = LoggingService.getInstance();
        this.trackingService = TrackingService.getInstance();
        this.alertService = AlertService.getInstance();
    }

    private void printProcessSensorLog(String sensor, double value) {
        loggingService.addLog("Processing " + crop + " " + sensor + " = " + value);
    }

    private String alertMessage(SensorType sensor, Breach breach) {
        String subject = sensor.isCropSpecific() ? chamber + " " + crop : chamber;
        return subject + " " + breach.getLabel() + " " + sensor.getAlertName();
    }

    public synchronized void process(SensorType sensor, double value, Usage usage) throws InterruptedException {
        printProcessSensorLog(sensor.getLogName(), value);
        state.set(sensor, value);

        Crop cropEnum = sensor.isCropSpecific() ? Crop.valueOf(crop.toUpperCase().replace(' ', '_')) : Crop.DEFAULT;
        CropProfile cropProfile = cropEnum.getCropProfile();
        Map<SensorType, SensorLimits> limitsMap = cropProfile.limits();

        if (!limitsMap.containsKey(sensor)) {
            return;
        }

        SensorLimits limits = limitsMap.get(sensor);
        Breach breach = value > limits.getHigh() ? Breach.HIGH : value < limits.getLow() ? Breach.LOW : null;

        if (breach == null) {
            return;
        }

        String message = alertMessage(sensor, breach);
        trackingService.trackAlert(chamber, message);
        alertService.addAlert(chamber, crop, message);

        usage.recordBreachUsage(cropProfile, sensor, breach);

        Control control = sensor.getControlToUse(breach);

        if (control != null) {
            controlQueue.addControlRequest(new ControlRequest(chamber, control));
        }
    }
}
