package org.svfrms.service;

import org.svfrms.entity.ChamberState;
import org.svfrms.service.events.AlertService;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;
import org.svfrms.service.strategy.HandleCropHumidityStrategy;
import org.svfrms.service.strategy.HandleCropLightLevelStrategy;
import org.svfrms.service.strategy.HandleCropNutrientLevelStrategy;
import org.svfrms.service.strategy.HandleCropSoilMoistureStrategy;
import org.svfrms.service.strategy.HandleCropTemperatureStrategy;
import org.svfrms.util.SensorLimits;
import org.svfrms.util.constants.SensorConstants;

public class ProcessSensorService {
    private String chamber;
    private String crop;

    private ChamberState state;
    private LoggingService loggingService;
    private TrackingService trackingService;
    private AlertService alertService;

    public ProcessSensorService(String chamber, String crop) {
        this.chamber = chamber;
        this.crop = crop;
        this.state = ChamberState.getChamberState(chamber);
        this.loggingService = new LoggingService(chamber);
        this.trackingService = new TrackingService(chamber);
        this.alertService = new AlertService(chamber, crop);
    }

    private void printProcessSensorLog(String sensor, double value) {
        loggingService.addLog("Processing " + crop + " " + sensor + " = " + value);
    }

    public void processTemperature(double value) {
        String sensor = "Temperature";
        printProcessSensorLog(sensor, value);
        state.setTemperature(value);
        if (SensorConstants.TEMPERATURE_LIMITS_MAP.containsKey(crop)) {
            String message = "";
            SensorLimits limits = SensorConstants.TEMPERATURE_LIMITS_MAP.get(crop);
            if (value > limits.getHigh()) {
                message = HandleCropTemperatureStrategy.valueOf(crop).temperatureHigh(chamber);
            } else if (value < limits.getLow()) {
                message = HandleCropTemperatureStrategy.valueOf(crop).temperatureLow(chamber);
            }
            if (!message.equals("")) {
                trackingService.trackAlert(message);
                alertService.addAlert(message);
            }
        }
    }

    public void processHumidity(double value) {
        String sensor = "Humidity";
        printProcessSensorLog(sensor, value);
        state.setHumidity(value);
        if (SensorConstants.HUMIDITY_LIMITS_MAP.containsKey(crop)) {
            String message = "";
            SensorLimits limits = SensorConstants.HUMIDITY_LIMITS_MAP.get(crop);
            if (value > limits.getHigh()) {
                message = HandleCropHumidityStrategy.valueOf(crop).humidityHigh(chamber);
            } else if (value < limits.getLow()) {
                message = HandleCropHumidityStrategy.valueOf(crop).humidityLow(chamber);
            }
            if (!message.equals("")) {
                trackingService.trackAlert(message);
                alertService.addAlert(message);
            }
        }
    }

    public void processSoilMoisture(double value) {
        String sensor = "Soil";
        printProcessSensorLog(sensor, value);
        state.setSoilMoisture(value);
        if (SensorConstants.SOIL_MOISTURE_LIMITS_MAP.containsKey(crop)) {
            SensorLimits limits = SensorConstants.SOIL_MOISTURE_LIMITS_MAP.get(crop);
            if (value < limits.getLow()) {
                String message = HandleCropSoilMoistureStrategy.valueOf(crop).soilMoistureLow(chamber);
                trackingService.trackAlert(message);
                alertService.addAlert(message);
            }
        }
    }

    public void processLightLevel(double value) {
        String sensor = "Light";
        printProcessSensorLog(sensor, value);
        state.setLightLevel(value);
        SensorLimits limits = SensorConstants.LIGHT_LEVEL_LIMITS_MAP.get(crop);
        if (value < limits.getLow()) {
            String message = (new HandleCropLightLevelStrategy()).lightLevelLow(chamber);
            trackingService.trackAlert(message);
            alertService.addAlert(message);
        }
    }

    public void processNutrientLevel(double value) {
        String sensor = "Nutrient";
        printProcessSensorLog(sensor, value);
        state.setLightLevel(value);
        SensorLimits limits = SensorConstants.NUTRIENT_LEVEL_LIMITS_MAP.get(crop);
        if (value < limits.getLow()) {
            String message = (new HandleCropNutrientLevelStrategy()).nutrientLevelLow(chamber);
            trackingService.trackAlert(message);
            alertService.addAlert(message);
        }
    }
}
