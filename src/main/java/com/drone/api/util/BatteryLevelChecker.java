package com.drone.api.util;

import com.drone.api.model.DroneDTO;
import com.drone.api.service.DroneServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@EnableScheduling
public class BatteryLevelChecker {

    private DroneServiceImpl droneService;

    BatteryLevelChecker(DroneServiceImpl droneService) {
        this.droneService = droneService;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 5; i++)
            System.out.println(UUID.randomUUID());
    }

    @Scheduled(cron = "00 03 * * * ?")
    public void batteryLevelChecker() {
        log.info("Checking all drones battery level");
        List<DroneDTO> drones = droneService.getAllDrones();

        if (drones != null) {
            for (DroneDTO drone : drones) {
                log.info("{} drone with serial number [{}], has {}% battery capacity",
                        drone.getModel(),
                        drone.getSerialNumber(),
                        drone.getBatteryCapacity() / 100);
            }
        } else
            log.info("No Drone found!");

        log.info("End of battery check");
    }
}
