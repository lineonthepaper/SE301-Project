package org.svfrms.runnable;

import java.util.Random;

import org.svfrms.entity.Usage;
import org.svfrms.enums.SensorType;
import org.svfrms.service.ProcessSensorService;

public class ChamberRunnable implements Runnable {
    private final ProcessSensorService processSensorService;
    private final Usage usage;
    private final Random random;

    public ChamberRunnable(ProcessSensorService processSensorService, Usage usage, Random random) {
        this.processSensorService = processSensorService;
        this.usage = usage;
        this.random = random;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                for (SensorType sensor : SensorType.values()) {
                    processSensorService.process(sensor, sensor.nextReading(random));
                }

                usage.recordWaterUsage(2);
                usage.recordEnergyUsage(1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
