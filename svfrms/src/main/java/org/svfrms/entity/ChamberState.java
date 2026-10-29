package org.svfrms.entity;

import java.util.HashMap;

public class ChamberState {
    private double temperature = 24;
    private double humidity = 70;
    private double soilMoisture = 60;
    private double lightLevel = 600;
    private double nutrientLevel = 70;

    public static HashMap<String, ChamberState> chamberStates = new HashMap<>();

    public static ChamberState getChamberState(String chamber) {
        if (!chamberStates.containsKey(chamber)) {
            chamberStates.put(chamber, new ChamberState());
        }
        return chamberStates.get(chamber);
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return this.humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSoilMoisture() {
        return this.soilMoisture;
    }

    public void setSoilMoisture(double soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public double getLightLevel() {
        return this.lightLevel;
    }

    public void setLightLevel(double lightLevel) {
        this.lightLevel = lightLevel;
    }

    public double getNutrientLevel() {
        return this.nutrientLevel;
    }

    public void setNutrientLevel(double nutrientLevel) {
        this.nutrientLevel = nutrientLevel;
    }
}
