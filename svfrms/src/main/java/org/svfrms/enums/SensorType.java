package org.svfrms.enums;

import java.util.Random;

public enum SensorType {
    TEMPERATURE("Temperature", "Temp", 24, true, 10, 30),
    HUMIDITY("Humidity", "Humidity", 70, true, 40, 60),
    SOIL("Soil", "Soil Moisture", 60, true, 20, 60),
    LIGHT("Light", "Light Level", 600, false, 300, 900),
    NUTRIENT("Nutrient", "Nutrient Level", 70, false, 20, 80);

    private final String logName;
    private final String alertName;
    private final double initialValue;
    private final boolean cropSpecific;
    private final int minReading;
    private final int readingRange;

    SensorType(String logName, String alertName, double initialValue, boolean cropSpecific, int minReading,
            int readingRange) {
        this.logName = logName;
        this.alertName = alertName;
        this.initialValue = initialValue;
        this.cropSpecific = cropSpecific;
        this.minReading = minReading;
        this.readingRange = readingRange;
    }

    public String getLogName() {
        return logName;
    }

    public String getAlertName() {
        return alertName;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public boolean isCropSpecific() {
        return cropSpecific;
    }

    public int nextReading(Random random) {
        return minReading + random.nextInt(readingRange);
    }

    public Control getControlToUse(Breach breach) {
        return switch (this) {
            case TEMPERATURE -> breach == Breach.HIGH ? Control.COOLING_FAN : null;
            case HUMIDITY -> breach == Breach.HIGH ? Control.DEHUMIDIFIER : Control.HUMIDIFIER;
            case SOIL -> breach == Breach.LOW ? Control.WATER_PUMP : null;
            case LIGHT -> breach == Breach.LOW ? Control.GROW_LIGHT : null;
            case NUTRIENT -> breach == Breach.LOW ? Control.NUTRIENT_INJECTOR : null;
        };
    }
}
