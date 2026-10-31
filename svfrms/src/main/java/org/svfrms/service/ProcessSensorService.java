package org.svfrms.service;

import java.util.Map;

import org.svfrms.entity.ChamberState;
import org.svfrms.entity.CropProfile;
import org.svfrms.entity.Usage;
import org.svfrms.enums.Breach;
import org.svfrms.enums.Control;
import org.svfrms.enums.Crop;
import org.svfrms.enums.SensorType;
import org.svfrms.service.control.ControlService;
import org.svfrms.service.events.AlertService;
import org.svfrms.service.events.LoggingService;
import org.svfrms.util.SensorLimits;

public class ProcessSensorService {
    private final String chamber;
    private final String crop;
    private final ControlService controlService;

    private final ChamberState state;
    private final LoggingService loggingService;
    private final AlertService alertService;
    private final Usage usage;

    public ProcessSensorService(String chamber, String crop, ChamberState state, ControlService controlService,
            LoggingService loggingService, AlertService alertService, Usage usage) {
        this.chamber = chamber;
        this.crop = crop;
        this.controlService = controlService;

        this.state = state;
        this.loggingService = loggingService;
        this.alertService = alertService;
        this.usage = usage;
    }

    private void printProcessSensorLog(String sensor, double value) {
        loggingService.addLog("Processing " + crop + " " + sensor + " = " + value);
    }

    private String alertMessage(SensorType sensor, Breach breach) {
        String subject = sensor.isCropSpecific() ? chamber + " " + crop : chamber;
        return subject + " " + breach.getLabel() + " " + sensor.getAlertName();
    }

    public synchronized void process(SensorType sensor, double value) throws InterruptedException {
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
        alertService.addAlert(chamber, crop, message);

        usage.recordBreachUsage(cropProfile, sensor, breach);

        Control control = sensor.getControlToUse(breach);

        if (control != null) {
            controlService.requestControl(chamber, control);
        }
    }
}
