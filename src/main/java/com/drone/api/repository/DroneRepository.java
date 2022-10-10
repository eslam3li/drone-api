package com.drone.api.repository;

import com.drone.api.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<DroneEntity, String> {
    List<DroneEntity> findDroneByState(String state);
}