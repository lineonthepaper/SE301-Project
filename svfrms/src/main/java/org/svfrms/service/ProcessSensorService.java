package org.svfrms.service;

import java.util.Map;

import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.ChamberState;
import org.svfrms.entity.Usage;
import org.svfrms.enums.Breach;
import org.svfrms.enums.Control;
import org.svfrms.enums.SensorType;
import org.svfrms.request.ControlRequest;
import org.svfrms.service.control.ControlService;
import org.svfrms.service.events.AlertService;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;
import org.svfrms.util.SensorLimits;
import org.svfrms.util.constants.SensorConstants;

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

    static String alertMessage(String chamber, String crop, SensorType sensor, Breach breach) {
        String subject = sensor.isCropSpecific() ? chamber + " " + crop : chamber;
        return subject + " " + breach.getLabel() + " " + sensor.getAlertName();
    }

    public void process(SensorType sensor, double value) {
        printProcessSensorLog(sensor.getLogName(), value);
        state.set(sensor, value);

        Map<String, SensorLimits> limitsMap = SensorConstants.SENSOR_LIMITS_MAP.get(sensor.name());
        if (limitsMap.containsKey(crop)) {
            SensorLimits limits = limitsMap.get(crop);
            Breach breach = value > limits.getHigh() ? Breach.HIGH : value < limits.getLow() ? Breach.LOW : null;
            if (breach != null) {
                Control control = sensor.getControlToUse(breach);
                if (control != null) {
                    controlService.applyControl(new ControlRequest(chamber, control));
                    String message = alertMessage(chamber, crop, sensor, breach);

                    trackingService.trackAlert(message);
                    alertService.addAlert(message);
                    Map<String, Map<Breach, Integer>> sensorEnergyMap = SensorConstants.ENERGY_USAGE_MAP.get(sensor);
                    if (sensorEnergyMap != null) {
                        Map<Breach, Integer> cropSensorEnergyMap = sensorEnergyMap.get(crop);
                        if (cropSensorEnergyMap != null) {
                            Usage.totalEnergyUsage += cropSensorEnergyMap.get(breach);
                        }
                    }

                    Map<String, Map<Breach, Integer>> sensorWaterMap = SensorConstants.WATER_USAGE_MAP.get(sensor);
                    if (sensorWaterMap != null) {
                        Map<Breach, Integer> cropSensorWaterMap = sensorWaterMap.get(crop);
                        if (cropSensorWaterMap != null) {
                            Usage.totalWaterUsage += cropSensorWaterMap.get(breach);
                        }
                    }
                }
            }
        }
    }
}
