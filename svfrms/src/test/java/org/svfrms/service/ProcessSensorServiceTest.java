package org.svfrms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svfrms.entity.Chamber;
import org.svfrms.entity.ChamberRegistry;
import org.svfrms.entity.Usage;
import org.svfrms.enums.Crop;
import org.svfrms.enums.SensorType;
import org.svfrms.runnable.ControlQueue;
import org.svfrms.service.control.ControlService;
import org.svfrms.service.events.AlertService;
import org.svfrms.service.events.LoggingService;
import org.svfrms.service.events.TrackingService;

public class ProcessSensorServiceTest {
    ChamberRegistry chamberRegistry;
    Usage usage;
    ReportService reportService;
    LoggingService loggingService;
    TrackingService trackingService;
    AlertService alertService;
    ControlQueue queue;
    ControlService controlService;

    @BeforeEach
    void init() {
        chamberRegistry = new ChamberRegistry();
        usage = new Usage();
        reportService = new ReportService(chamberRegistry, usage);
        loggingService = new LoggingService();
        trackingService = new TrackingService(chamberRegistry);
        alertService = new AlertService(trackingService);
        queue = new ControlQueue();
        controlService = new ControlService(chamberRegistry, trackingService, loggingService, queue);
    }

    @Test
    void tomatoTemperatureWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.TOMATO);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 28);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void tomatoTemperatureAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.TOMATO);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 29);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 10);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void tomatoTemperatureBelowBoundaryTriggersAlert() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.TOMATO);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 21);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 15);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void tomatoSoilBelowBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.TOMATO);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.SOIL, 44);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 50);
    }

    @Test
    void tomatoSoilWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.TOMATO);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.SOIL, 45);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void strawberryHumidityBelowBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.STRAWBERRY);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.HUMIDITY, 69);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 25);
    }

    @Test
    void strawberryHumidityWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.STRAWBERRY);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.HUMIDITY, 70);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void lightWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CUCUMBER);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.LIGHT, 500);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void lightUnderBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CUCUMBER);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.LIGHT, 499);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void nutrientUnderBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CHILI);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.NUTRIENT, 39);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void nutrientWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CHILI);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.NUTRIENT, 40);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }
}
