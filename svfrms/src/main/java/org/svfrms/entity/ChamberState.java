package org.svfrms.entity;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import org.svfrms.enums.SensorType;

public class ChamberState {
    private final Map<SensorType, Double> readings = new EnumMap<>(SensorType.class);

    public ChamberState() {
        for (SensorType type : SensorType.values()) {
            readings.put(type, type.getInitialValue());
        }
    }

    public synchronized void set(SensorType type, double value) {
        readings.put(type, value);
    }

    public synchronized double get(SensorType type) {
        return readings.get(type);
    }

    public synchronized double update(SensorType type, DoubleUnaryOperator change) {
        double updated = change.applyAsDouble(readings.get(type));
        readings.put(type, updated);
        return updated;
    }
}
