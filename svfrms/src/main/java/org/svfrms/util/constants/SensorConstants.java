package org.svfrms.util.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.svfrms.util.Breach;
import org.svfrms.util.SensorLimits;

public class SensorConstants {
        public static final List<String> CROPS = List.of(
                        "Tomato",
                        "Strawberry",
                        "Lettuce",
                        "Spinach",
                        "Chili",
                        "Cucumber");
        public static final Map<String, SensorLimits> TEMPERATURE_LIMITS_MAP = Map.of(
                        "Tomato", new SensorLimits(28, 22),
                        "Strawberry", new SensorLimits(24, 18),
                        "Lettuce", new SensorLimits(21, 15),
                        "Spinach", new SensorLimits(20, 10),
                        "Chili", new SensorLimits(32, 24),
                        "Cucumber", new SensorLimits(27, 18));
        public static final Map<String, SensorLimits> HUMIDITY_LIMITS_MAP = Map.of(
                        "Tomato", new SensorLimits(80, 60),
                        "Strawberry", new SensorLimits(85, 70));
        public static final Map<String, SensorLimits> SOIL_MOISTURE_LIMITS_MAP = Map.of(
                        "Tomato", new SensorLimits(45));
        public static final Map<String, SensorLimits> LIGHT_LEVEL_LIMITS_MAP = CROPS.stream()
                        .reduce(
                                        new HashMap<String, SensorLimits>(), (map, element) -> {
                                                map.put(element, new SensorLimits(500));
                                                return map;
                                        },
                                        (map1, map2) -> {
                                                map1.putAll(map2);
                                                return map1;
                                        });
        public static final Map<String, SensorLimits> NUTRIENT_LEVEL_LIMITS_MAP = CROPS.stream()
                        .reduce(
                                        new HashMap<String, SensorLimits>(), (map, element) -> {
                                                map.put(element, new SensorLimits(40));
                                                return map;
                                        },
                                        (map1, map2) -> {
                                                map1.putAll(map2);
                                                return map1;
                                        });

        public static final Map<String, Map<String, SensorLimits>> SENSOR_LIMITS_MAP = Map.of(
                        "TEMPERATURE", TEMPERATURE_LIMITS_MAP,
                        "HUMIDITY", HUMIDITY_LIMITS_MAP,
                        "SOIL", SOIL_MOISTURE_LIMITS_MAP,
                        "LIGHT", LIGHT_LEVEL_LIMITS_MAP,
                        "NUTRIENT", NUTRIENT_LEVEL_LIMITS_MAP);

        public static final Map<String, Map<String, Map<Breach, Integer>>> ENERGY_USAGE_MAP = Map.of(
                        "TEMPERATURE", Map.of(
                                        "Tomato", Map.of(
                                                        Breach.HIGH, 10,
                                                        Breach.LOW, 15),
                                        "Strawberry", Map.of(
                                                        Breach.HIGH, 10,
                                                        Breach.LOW, 15)));
        public static final Map<String, Map<String, Map<Breach, Integer>>> WATER_USAGE_MAP = Map.of(
                        "HUMIDITY", Map.of(
                                        "Tomato", Map.of(
                                                        Breach.LOW, 20),
                                        "Strawberry", Map.of(
                                                        Breach.LOW, 25)),
                        "SOIL", Map.of(
                                        "Tomato", Map.of(
                                                        Breach.LOW, 50)));
}
