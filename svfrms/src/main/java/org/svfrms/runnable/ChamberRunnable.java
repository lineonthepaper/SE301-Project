package org.svfrms.runnable;

import java.util.Random;

import org.svfrms.entity.Usage;
import org.svfrms.enums.SensorType;
import org.svfrms.service.ProcessSensorService;

public class ChamberRunnable implements Runnable {
    private final ProcessSensorService processSensorService;
    private static final Random random = new Random();

    public ChamberRunnable(String chamber, String crop, ControlQueue controlQueue) {
        this.processSensorService = new ProcessSensorService(chamber, crop, controlQueue);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                processSensorService.process(SensorType.TEMPERATURE, random.nextInt(30));
                processSensorService.process(SensorType.HUMIDITY, random.nextInt(60));
                processSensorService.process(SensorType.SOIL, random.nextInt(60));
                processSensorService.process(SensorType.LIGHT, random.nextInt(900));
                processSensorService.process(SensorType.NUTRIENT, random.nextInt(80));

                Usage.recordWaterUsage(2);
                Usage.recordEnergyUsage(1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
