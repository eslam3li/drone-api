package com.drone.api.service;

import com.drone.api.entity.DroneEntity;
import com.drone.api.fixture.DroneFixture;
import com.drone.api.model.DroneDTO;
import com.drone.api.repository.DroneRepository;
import com.drone.api.repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private DroneServiceImpl droneService;

    private DroneDTO droneDTO;
    private DroneEntity droneEntity;


    @BeforeEach
    void init() {
        droneEntity = DroneFixture.getDroneEntity();
        droneDTO = DroneFixture.getDroneDTO();
    }

    @Test
    public void givenValidNewDrone_whenRegisterDrone_thenRegistrationSuccess() {
        when(droneRepository.existsById(any())).thenReturn(false);
        when(droneRepository.save(any(DroneEntity.class))).thenReturn(droneEntity);

        DroneDTO savedDrone = droneService.registerDrone(droneDTO);

        assertEquals(savedDrone.getSerialNumber(), droneDTO.getSerialNumber());
        assertEquals(savedDrone.getModel(), droneDTO.getModel());
    }

    @Test
    public void givenRegisteredDrone_whenRegisterDrone_ThrowException() {
        when(droneRepository.existsById(any())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> droneService.registerDrone(droneDTO));
    }
}
