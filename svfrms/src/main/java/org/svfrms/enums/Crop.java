package org.svfrms.enums;

import java.util.Map;

import org.svfrms.entity.CropProfile;
import org.svfrms.util.BreachUsage;
import org.svfrms.util.SensorLimits;

public enum Crop {
    TOMATO(
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(28, 22),
                            SensorType.HUMIDITY, new SensorLimits(80, 60),
                            SensorType.SOIL, new SensorLimits(45)),
                    Map.of(SensorType.TEMPERATURE, new BreachUsage(10, 15)),
                    Map.of(
                            SensorType.HUMIDITY, new BreachUsage(0, 20),
                            SensorType.SOIL, new BreachUsage(0, 50)))),
    STRAWBERRY(
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(24, 18),
                            SensorType.HUMIDITY, new SensorLimits(85, 70)),
                    Map.of(
                            SensorType.TEMPERATURE, new BreachUsage(10, 15)),
                    Map.of(
                            SensorType.HUMIDITY, new BreachUsage(0, 25)))),
    LETTUCE(
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(21, 15)),
                    Map.of(), Map.of())),
    SPINACH(
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(20, 10)),
                    Map.of(), Map.of())),
    CHILI(
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(32, 24)),
                    Map.of(), Map.of())),
    CUCUMBER(
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(27, 18)),
                    Map.of(), Map.of())),
    KALE(
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(24, 7)),
                    Map.of(), Map.of())),
    DEFAULT(
            new CropProfile(
                    Map.of(
                            SensorType.LIGHT, new SensorLimits(500),
                            SensorType.NUTRIENT, new SensorLimits(40)),
                    Map.of(), Map.of()));

    private final CropProfile cropProfile;

    Crop(
            CropProfile cropProfile) {
        this.cropProfile = cropProfile;
    }

    public CropProfile getCropProfile() {
        return this.cropProfile;
    }
}
