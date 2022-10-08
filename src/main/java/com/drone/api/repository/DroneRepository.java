package com.drone.api.repository;

import com.drone.api.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findDroneByState(String state);
}