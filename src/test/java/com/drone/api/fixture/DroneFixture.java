package com.drone.api.fixture;

import com.drone.api.entity.DroneEntity;
import com.drone.api.model.DroneDTO;
import com.drone.api.model.enums.DroneModelWeightLimit;
import com.drone.api.model.enums.DroneState;
import org.modelmapper.ModelMapper;

import java.util.UUID;

public class DroneFixture {
    private DroneFixture() {
        throw new UnsupportedOperationException();
    }

    public static DroneEntity getDroneEntity() {
        DroneEntity droneEntity = new DroneEntity();
        droneEntity.setSerialNumber(UUID.randomUUID().toString());
        droneEntity.setModel(DroneModelWeightLimit.HEAVY_WEIGHT.getModel());
        droneEntity.setWeightLimit(DroneModelWeightLimit.HEAVY_WEIGHT.getWeightLimit());
        droneEntity.setState(DroneState.IDLE.name());
        droneEntity.setBatteryCapacity(50.0);

        return droneEntity;
    }

    public static DroneDTO getDroneDTO() {

        return new ModelMapper().map(getDroneEntity(), DroneDTO.class);
    }
}
