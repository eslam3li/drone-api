package com.drone.api.service;

import com.drone.api.model.MedicationDTO;

import java.util.List;

public interface MedicationService {
    List<MedicationDTO> getAllMedications();

    List<MedicationDTO> getMedications(List<String> codes);

    MedicationDTO getMedication(String code);

    MedicationDTO addMedication(MedicationDTO medicationDTO);
}
