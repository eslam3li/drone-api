package com.drone.api.service;

import com.drone.api.entity.Medication;
import com.drone.api.model.MedicationDTO;
import com.drone.api.repository.MedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    private final ModelMapper mapper = new ModelMapper();

    public MedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }


    @Override
    public List<MedicationDTO> getAllMedications() {
        return medicationRepository.findAll()
                .stream()
                .map(medication -> mapper.map(medication, MedicationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicationDTO> getMedications(List<String> codes) {
        return medicationRepository.findAllById(codes)
                .stream()
                .map(medication -> mapper.map(medication, MedicationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicationDTO getMedication(String code) {
        return medicationRepository.findById(code)
                .map(medication -> mapper.map(medication, MedicationDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public MedicationDTO addMedication(MedicationDTO medicationDTO) {
        Medication medication = mapper.map(medicationDTO, Medication.class);
        medicationRepository.save(medication);
        return medicationDTO;
    }

}