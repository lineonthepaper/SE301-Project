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
    void strawberryTemperatureWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.STRAWBERRY);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 24);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void strawberryTemperatureAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.STRAWBERRY);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 25);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 10);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void strawberryTemperatureBelowBoundaryTriggersAlert() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.STRAWBERRY);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 17);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 15);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void lettuceTemperatureWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.LETTUCE);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 21);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void lettuceTemperatureAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.LETTUCE);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 22);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void lettuceTemperatureBelowBoundaryTriggersAlert() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.LETTUCE);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 14);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void spinachTemperatureWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.SPINACH);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 20);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void spinachTemperatureAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.SPINACH);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 21);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void spinachTemperatureBelowBoundaryTriggersAlert() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.SPINACH);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 9);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void chiliTemperatureWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CHILI);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 32);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void chiliTemperatureAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CHILI);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 33);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void chiliTemperatureBelowBoundaryTriggersAlert() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CHILI);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 23);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void cucumberTemperatureWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CUCUMBER);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 27);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void cucumberTemperatureAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CUCUMBER);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 28);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void cucumberTemperatureBelowBoundaryTriggersAlert() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.CUCUMBER);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 17);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void kaleTemperatureWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.KALE);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 24);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void kaleTemperatureAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.KALE);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 25);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void kaleTemperatureBelowBoundaryTriggersAlert() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.KALE);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.TEMPERATURE, 6);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
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
    void strawberryHumidityAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.STRAWBERRY);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.HUMIDITY, 86);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void tomatoHumidityBelowBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.TOMATO);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.HUMIDITY, 59);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 20);
    }

    @Test
    void tomatoHumidityWithinBoundaryDoesNotTriggerAlertOrControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.TOMATO);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.HUMIDITY, 80);
        assertEquals(alertService.getAlerts().size(), 0);
        assertEquals(queue.getQueue().size(), 0);
        assertEquals(usage.getTotalEnergyUsage(), 0);
        assertEquals(usage.getTotalWaterUsage(), 0);
    }

    @Test
    void tomatoHumidityAboveBoundaryTriggersAlertAndControl() throws InterruptedException {
        Chamber chamber = new Chamber("CH1", Crop.TOMATO);

        ProcessSensorService processSensorService = new ProcessSensorService(chamber,
                chamberRegistry.getState(chamber), controlService, loggingService, alertService, usage);

        processSensorService.process(SensorType.HUMIDITY, 81);
        assertEquals(alertService.getAlerts().size(), 1);
        assertEquals(queue.getQueue().size(), 1);
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
