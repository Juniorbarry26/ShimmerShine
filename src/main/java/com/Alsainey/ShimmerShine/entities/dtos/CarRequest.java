package com.Alsainey.ShimmerShine.entities.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 1000, message = "Year must be a 4-digit number")
    @Max(value = 9999, message = "Year must be a 4-digit number")
    private Integer year;
    private String nickname;
}