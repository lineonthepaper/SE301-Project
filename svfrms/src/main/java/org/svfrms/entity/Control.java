package org.svfrms.entity;

import java.util.function.DoubleUnaryOperator;

public enum Control {
    COOLING_FAN("Cooling Fan", SensorType.TEMPERATURE, t -> Math.max(18, t - 5), "Temperature reduced to %sC"),
    WATER_PUMP("Water Pump", SensorType.SOIL, s -> Math.min(100, s + 20), "Soil moisture increased to %s%%"),
    NUTRIENT_INJECTOR("Nutrient Injector", SensorType.NUTRIENT, n -> Math.min(100, n + 15),
            "Nutrient level increased to %s%%"),
    HUMIDIFIER("Humidifier", SensorType.HUMIDITY, h -> Math.min(100, h + 10), "Humidity increased to %s%%"),
    DEHUMIDIFIER("Dehumidifier", SensorType.HUMIDITY, h -> Math.max(30, h - 10), "Humidity reduced to %s%%"),
    GROW_LIGHT("Grow Light", SensorType.LIGHT, l -> Math.min(1000, l + 150), "Light level increased to %s lux");

    private final String label;
    private final SensorType target;
    private final DoubleUnaryOperator effect;
    private final String resultFormat;

    Control(String label, SensorType target, DoubleUnaryOperator effect, String resultFormat) {
        this.label = label;
        this.target = target;
        this.effect = effect;
        this.resultFormat = resultFormat;
    }

    public String getLabel() {
        return label;
    }

    public String apply(ChamberState state) {
        double newValue = state.update(target, effect);
        return resultFormat.formatted(newValue);
    }
}
