package org.svfrms.enums;

import java.util.Map;

import org.svfrms.entity.CropProfile;
import org.svfrms.util.BreachUsage;
import org.svfrms.util.SensorLimits;

public enum Crop {
    TOMATO(
            "Tomato",
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
            "Strawberry",
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(24, 18),
                            SensorType.HUMIDITY, new SensorLimits(85, 70)),
                    Map.of(
                            SensorType.TEMPERATURE, new BreachUsage(10, 15)),
                    Map.of(
                            SensorType.HUMIDITY, new BreachUsage(0, 25)))),
    LETTUCE(
            "Lettuce",
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(21, 15)),
                    Map.of(), Map.of())),
    SPINACH(
            "Spinach",
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(20, 10)),
                    Map.of(), Map.of())),
    CHILI(
            "Chili",
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(32, 24)),
                    Map.of(), Map.of())),
    CUCUMBER(
            "Cucumber",
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(27, 18)),
                    Map.of(), Map.of())),
    KALE(
            "Kale",
            new CropProfile(
                    Map.of(
                            SensorType.TEMPERATURE, new SensorLimits(24, 7)),
                    Map.of(), Map.of())),
    DEFAULT(
            "Default",
            new CropProfile(
                    Map.of(
                            SensorType.LIGHT, new SensorLimits(500),
                            SensorType.NUTRIENT, new SensorLimits(40)),
                    Map.of(), Map.of()));

    private final String label;
    private final CropProfile cropProfile;

    Crop(
            String label,
            CropProfile cropProfile) {
        this.label = label;
        this.cropProfile = cropProfile;
    }

    public String getLabel() {
        return this.label;
    }

    public CropProfile getCropProfile() {
        return this.cropProfile;
    }
}
