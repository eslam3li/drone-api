package com.drone.api.repos;

import com.drone.api.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicationRepository extends JpaRepository<Medication, String> {
}