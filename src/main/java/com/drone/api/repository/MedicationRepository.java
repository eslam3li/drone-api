package com.drone.api.repository;

import com.drone.api.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<MedicationEntity, String> {
}