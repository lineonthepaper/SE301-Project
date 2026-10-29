package org.svfrms.service;

import org.svfrms.entity.ChamberState;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;

public class ControlService {
    private ChamberState state;
    private TrackingService trackingService;
    private LoggingService loggingService;

    public ControlService(String chamber) {
        this.state = ChamberState.getChamberState(chamber);
        this.trackingService = new TrackingService(chamber);
        this.loggingService = new LoggingService(chamber);
    }

    public void applyControl(String control) {
        switch (control) {
            case "Cooling Fan" -> applyCoolingFan();
            case "Water Pump" -> applyWaterPump();
            case "Nutrient Injector" -> applyNutrientInjector();
            case "Humidifier" -> applyHumidifier();
            case "Dehumidifier" -> applyDehumidifier();
            case "Grow Light" -> applyGrowLight();
            default -> System.out.println("Unknown control");
        }
    }

    public void applyCoolingFan() {
        state.setTemperature(Math.max(18, state.getTemperature() - 5));
        trackingService.trackAction("Cooling Fan Activated");
        loggingService.printControlLog("Cooling Fan Activated");
        loggingService.printControlLog("Temperature reduced to " + state.getTemperature() + "C");
    }

    public void applyWaterPump() {
        state.setSoilMoisture(Math.min(100, state.getSoilMoisture() + 20));
        trackingService.trackAction("Water Pump Activated");
        loggingService.printControlLog("Water Pump Activated");
        loggingService.printControlLog("Soil moisture increased to " + state.getSoilMoisture() + "%");
    }

    public void applyNutrientInjector() {
        state.setNutrientLevel(Math.min(100, state.getNutrientLevel() + 15));
        trackingService.trackAction("Nutrient Injector Activated");
        loggingService.printControlLog("Nutrient Injector Activated");
        loggingService.printControlLog("Nutrient level increased to " + state.getNutrientLevel() + "%");
    }

    public void applyHumidifier() {
        state.setHumidity(Math.min(100, state.getHumidity() + 10));
        trackingService.trackAction("Humidifier Activated");
        loggingService.printControlLog("Humidifier Activated");
        loggingService.printControlLog("Humidity increased to " + state.getHumidity() + "%");
    }

    public void applyDehumidifier() {
        state.setHumidity(Math.max(30, state.getHumidity() - 10));
        trackingService.trackAction("Dehumidifier Activated");
        loggingService.printControlLog("Dehumidifier Activated");
        loggingService.printControlLog("Humidity reduced to " + state.getHumidity() + "%");
    }

    public void applyGrowLight() {
        state.setLightLevel(Math.min(1000, state.getLightLevel() + 150));
        trackingService.trackAction("Grow Light Activated");
        loggingService.printControlLog("Grow Light Activated");
        loggingService.printControlLog("Light level increased to " + state.getLightLevel() + " lux");
    }
}
