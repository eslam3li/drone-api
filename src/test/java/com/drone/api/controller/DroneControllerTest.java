package com.drone.api.controller;

import com.drone.api.DroneApplication;
import com.drone.api.TestUtils;
import com.drone.api.exception.DroneExceptionHandler;
import com.drone.api.model.DroneDTO;
import com.drone.api.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DroneApplication.class)
class DroneControllerTest {

    private static final String BASE_URL = "/api/";
    private static final String REGISTER_DRONE = "drone";

    private MockMvc mockMvc;

    @Mock
    private DroneService droneService;

    @InjectMocks
    private DroneController droneController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(droneController)
                .setControllerAdvice(new DroneExceptionHandler()).build();
    }

    @Test
    void whenValidDronePostRequest_ThenStatusIs200() throws Exception {
        DroneDTO droneDTO = new DroneDTO();
        droneDTO.setSerialNumber(UUID.randomUUID().toString());
        droneDTO.setModel("Heavyweight");
        droneDTO.setWeightLimit(500.0);
        droneDTO.setBatteryCapacity(50.0);
        droneDTO.setState("IDLE");
        String requestBody = TestUtils.getMapper()
                .writeValueAsString(droneDTO);
        when(droneService.registerDrone(isA(DroneDTO.class))).thenReturn(isA(DroneDTO.class));

        mockMvc.perform(post(BASE_URL + REGISTER_DRONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void whenInValidDronePostRequest_ThenStatusIs400() throws Exception {
        DroneDTO droneDTO = new DroneDTO();
        //droneDTO.setSerialNumber(UUID.randomUUID().toString());
        droneDTO.setModel("Heavyweight");
        droneDTO.setWeightLimit(500.0);
        droneDTO.setBatteryCapacity(50.0);
        droneDTO.setState("IDLE");
        String requestBody = TestUtils.getMapper()
                .writeValueAsString(droneDTO);

        mockMvc.perform(post(BASE_URL + REGISTER_DRONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

}
