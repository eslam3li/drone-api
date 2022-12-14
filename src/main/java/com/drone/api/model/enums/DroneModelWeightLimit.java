package com.drone.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DroneModelWeightLimit {

    LIGHT_WEIGHT("Lightweight", 200.0),
    MIDDLE_WEIGHT("Middleweight", 300.0),
    CRUISER_WEIGHT("Cruiserweight", 400.0),
    HEAVY_WEIGHT("Heavyweight", 500.0);

    private String model;
    private double weightLimit;

    public static double getWeightLimit(String model) {
        for (DroneModelWeightLimit b : DroneModelWeightLimit.values()) {
            if (String.valueOf(b.model).equals(model)) {
                return b.weightLimit;
            }
        }
        return LIGHT_WEIGHT.weightLimit;
    }
}
