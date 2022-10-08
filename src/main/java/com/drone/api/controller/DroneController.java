package com.drone.api.controller;

import com.drone.api.model.DroneDTO;
import com.drone.api.model.MedicationDTO;
import com.drone.api.service.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping("drone")
    public ResponseEntity<DroneDTO> registerDrone(@RequestBody @Valid DroneDTO droneDTO) {
        return ResponseEntity.ok(droneService.registerDrone(droneDTO));
    }

    @PutMapping("drone/medications")
    public ResponseEntity<DroneDTO> loadDroneWithMedications(@RequestParam("serialnumber") @Valid String serialNumber,
                                                             @RequestParam("medicationcodes") @Valid List<String> medicationCodes) {
        return ResponseEntity.ok(droneService.loadDroneMedications(serialNumber, medicationCodes));
    }

    @GetMapping("drone/medications")
    public ResponseEntity<List<MedicationDTO>> getDroneMedications(@RequestParam("serialnumber") @Valid String serialNumber) {
        return ResponseEntity.ok(droneService.getDroneMedications(serialNumber));
    }

    @GetMapping("drones/loadable")
    public ResponseEntity<List<DroneDTO>> getLoadableDrones() {
        return ResponseEntity.ok(droneService.getLoadableDrones());
    }

    @GetMapping("drone/battery")
    public ResponseEntity<Double> getDroneBatteryLevel(@RequestParam("serialnumber") @Valid String serialNumber) {
        return ResponseEntity.ok(droneService.getDroneBatteryLevel(serialNumber));
    }

}