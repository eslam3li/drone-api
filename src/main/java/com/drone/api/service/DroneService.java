package com.drone.api.service;

import com.drone.api.model.DroneDTO;
import com.drone.api.model.MedicationDTO;

import java.util.List;

public interface DroneService {
    DroneDTO registerDrone(DroneDTO droneDTO);

    DroneDTO loadDroneMedications(String serialNumber, List<String> medicationCodes);

    List<MedicationDTO> getDroneMedications(String serialNumber);

    List<DroneDTO> getLoadableDrones();

    double getDroneBatteryLevel(String serialNumber);

}
