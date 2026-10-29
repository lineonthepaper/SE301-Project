package org.svfrms.thread;

import java.util.Random;

import org.svfrms.entity.Usage;
import org.svfrms.service.ProcessSensorService;

public class ChamberThread extends Thread {
    private ProcessSensorService processSensorService;
    private static Random random = new Random();

    public ChamberThread(String chamber, String crop) {
        this.processSensorService = new ProcessSensorService(chamber, crop);
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            processSensorService.processTemperature(random.nextInt(30));
            processSensorService.processHumidity(random.nextInt(60));
            processSensorService.processSoilMoisture(random.nextInt(60));
            processSensorService.processLightLevel(random.nextInt(900));
            processSensorService.processNutrientLevel(random.nextInt(80));
            Usage.totalWaterUsage += 2;
            Usage.totalEnergyUsage += 1;
        }
    }
}
