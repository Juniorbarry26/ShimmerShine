package com.Alsainey.ShimmerShine.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarRequest {

    @NotBlank(message = "License plate is required")
    private String licensePlate;

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    // Optional fields
    private String color;
    private Integer year;
    private String nickname;
}