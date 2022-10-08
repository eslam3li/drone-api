package com.drone.api.service;

import com.drone.api.entity.Drone;
import com.drone.api.entity.Medication;
import com.drone.api.model.DroneDTO;
import com.drone.api.model.MedicationDTO;
import com.drone.api.model.enums.DroneModelWeightLimit;
import com.drone.api.model.enums.DroneState;
import com.drone.api.repository.DroneRepository;
import com.drone.api.repository.MedicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
public class DroneServiceImpl implements DroneService {

    private final double MIN_BATTERY_CAPACITY = 25;
    private final ModelMapper mapper = new ModelMapper();
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public DroneServiceImpl(DroneRepository droneRepository,
                            MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public DroneDTO registerDrone(DroneDTO droneDTO) {
        log.info("Register drone...");
        boolean droneExist = droneRepository.existsById(droneDTO.getSerialNumber());
        Drone drone;

        if (!droneExist) {
            droneDTO.setWeightLimit(DroneModelWeightLimit.getWeightLimit(droneDTO.getModel()));
            droneDTO.setState(DroneState.IDLE.name());
            drone = mapper.map(droneDTO, Drone.class);
            droneRepository.save(drone);
        } else {
            log.warn("Drone already registered.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drone already registered.");
        }

        log.info("Drone registered with the following details: {}.", droneDTO);
        return mapper.map(drone, DroneDTO.class);
    }

    @Override
    public DroneDTO loadDroneMedications(String serialNumber, List<String> medicationCodes) {

        log.info("Load a drone [{}] with medication items...", serialNumber);
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));

        boolean isLoadable = checkLoadable(drone.getBatteryCapacity());

        if (isLoadable) {
            drone.setState(DroneState.LOADING.name());
            final int minSize = 0;

            log.info("Fetching medication items data from MEDICATION TABLE.");
            List<Medication> medications = medicationRepository.findAllById(medicationCodes);
            if (medications.size() > minSize) {
                if (checkWeightLimit(medications, drone.getModel())) {
                    drone.setMedications(medications);
                    drone.setState(DroneState.LOADED.name());
                    droneRepository.save(drone);
                    log.info("Medication items data loaded.");
                    return mapper.map(drone, DroneDTO.class);
                } else {
                    log.error("Medication(s) weight exceeds drone weight limit.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medication(s) weight exceeds drone weight limit ("
                            + DroneModelWeightLimit.getWeightLimit(drone.getModel()) + "gr)");
                }
            } else {
                log.error("Can not load medication(s): Invalid medication(s) code.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can not load medication(s): Invalid medication(s) code");
            }
        } else
            log.error("Can not load medication(s): Drone battery capacity below 25%");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not load medication(s): Drone battery capacity below 25%");

    }

    @Override
    public List<DroneDTO> getLoadableDrones() {
        log.info("Get loadable drones...");
        List<Drone> drones = droneRepository.findDroneByState(DroneState.IDLE.name());
        List<DroneDTO> result = new ArrayList<>();
        if (drones != null) {
            for (Drone droneValue : drones) {
                if (checkLoadable(droneValue.getBatteryCapacity())) {
                    result.add(mapper.map(droneValue, DroneDTO.class));
                }
            }
            log.info("Successfully fetched loadable drones.");
        }
        return result;
    }

    @Override
    public List<MedicationDTO> getDroneMedications(String serialNumber) {
        log.error("Get medications loaded on a drone...");
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));

        List<Medication> meds = drone.getMedications();

        if (meds != null) {
            log.info("Successfully fetched medications loaded on drone [{}].", serialNumber);
            return drone.getMedications().stream()
                    .map(medication ->
                            mapper.map(medication, MedicationDTO.class))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public double getDroneBatteryLevel(String serialNumber) {
        log.info("Getting drone[{}] battery level...", serialNumber);
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found: Invalid serial number"));
        log.info("Fetched drone[{}] battery level.", serialNumber);
        return drone.getBatteryCapacity();
    }


    public List<DroneDTO> getAllDrones() {
        return droneRepository.findAll()
                .stream()
                .map(drone -> mapper.map(drone, DroneDTO.class))
                .collect(Collectors.toList());
    }

    private boolean checkWeightLimit(List<Medication> medications, String model) {

        double medsWeight = 0.0;
        for (Medication medication : medications) {
            medsWeight += medication.getWeight();
        }

        return DroneModelWeightLimit.getWeightLimit(model) >= medsWeight;
    }

    private boolean checkLoadable(double batteryCapacity) {
        return batteryCapacity > MIN_BATTERY_CAPACITY;
    }

}