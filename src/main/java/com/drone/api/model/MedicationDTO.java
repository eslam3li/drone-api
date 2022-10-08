package com.drone.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class MedicationDTO {

    @NotNull
    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;

    @NotNull
    @Pattern(regexp = "^[\\w-]+$")
    private String name;

    @NotNull
    private Double weight;

    private String image;

}