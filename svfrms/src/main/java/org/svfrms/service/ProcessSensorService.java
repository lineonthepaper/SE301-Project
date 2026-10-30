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
import org.svfrms.service.control.ControlService;
import org.svfrms.service.events.AlertService;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;
import org.svfrms.util.EnergyUsage;
import org.svfrms.util.SensorLimits;
import org.svfrms.util.WaterUsage;

public class ProcessSensorService {
    private String chamber;
    private String crop;

    private ChamberState state;
    private LoggingService loggingService;
    private TrackingService trackingService;
    private AlertService alertService;
    private ControlService controlService;

    public ProcessSensorService(String chamber, String crop) {
        this.chamber = chamber;
        this.crop = crop;
        this.state = ChamberRegistry.getState(chamber);
        this.loggingService = new LoggingService(chamber);
        this.trackingService = new TrackingService(chamber);
        this.alertService = new AlertService(chamber, crop);
        this.controlService = new ControlService(chamber);
    }

    private void printProcessSensorLog(String sensor, double value) {
        loggingService.addLog("Processing " + crop + " " + sensor + " = " + value);
    }

    private String alertMessage(SensorType sensor, Breach breach) {
        String subject = sensor.isCropSpecific() ? chamber + " " + crop : chamber;
        return subject + " " + breach.getLabel() + " " + sensor.getAlertName();
    }

    private void recordUsage(CropProfile cropProfile, SensorType sensor, Breach breach) {
        EnergyUsage energyUsage = cropProfile.energyUsageMap().get(sensor);
        if (energyUsage != null) {
            Usage.totalEnergyUsage += breach == Breach.HIGH ? energyUsage.getHighBreachUsage()
                    : energyUsage.getLowBreachUsage();
        }

        WaterUsage waterUsage = cropProfile.waterUsageMap().get(sensor);
        if (waterUsage != null) {
            Usage.totalWaterUsage += breach == Breach.HIGH ? waterUsage.getHighBreachUsage()
                    : waterUsage.getLowBreachUsage();
        }
    }

    public void process(SensorType sensor, double value) {
        printProcessSensorLog(sensor.getLogName(), value);
        state.set(sensor, value);

        Crop cropEnum = sensor.isCropSpecific() ? Crop.valueOf(crop.toUpperCase()) : Crop.DEFAULT;
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

        Control control = sensor.getControlToUse(breach);

        if (control == null) {
            return;
        }

        controlService.applyControl(new ControlRequest(chamber, control));

        String message = alertMessage(sensor, breach);
        trackingService.trackAlert(message);
        alertService.addAlert(message);

        recordUsage(cropProfile, sensor, breach);
    }
}
