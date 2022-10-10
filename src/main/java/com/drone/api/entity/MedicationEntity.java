package com.drone.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class MedicationEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double weight;

    @Column
    private String image;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "medications")
    private List<DroneEntity> drones;

}