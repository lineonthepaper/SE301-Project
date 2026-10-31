package org.svfrms.runnable;

import java.util.Random;

import org.svfrms.entity.Usage;
import org.svfrms.enums.SensorType;
import org.svfrms.service.ProcessSensorService;

public class ChamberRunnable implements Runnable {
    private final ProcessSensorService processSensorService;
    private final Usage usage;
    private static final Random random = new Random();

    public ChamberRunnable(ProcessSensorService processSensorService, Usage usage) {
        this.processSensorService = processSensorService;
        this.usage = usage;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                processSensorService.process(SensorType.TEMPERATURE, 10 + random.nextInt(30));
                processSensorService.process(SensorType.HUMIDITY, 40 + random.nextInt(60));
                processSensorService.process(SensorType.SOIL, 20 + random.nextInt(60));
                processSensorService.process(SensorType.LIGHT, 300 + random.nextInt(900));
                processSensorService.process(SensorType.NUTRIENT, 20 + random.nextInt(80));

                usage.recordWaterUsage(2);
                usage.recordEnergyUsage(1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
