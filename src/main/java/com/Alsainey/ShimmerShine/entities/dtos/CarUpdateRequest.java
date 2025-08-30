package com.Alsainey.ShimmerShine.entities.dtos;

import lombok.Data;

@Data
public class CarUpdateRequest {

    private String licensePlate; // optional, but usually not updatable
    private String make;
    private String model;
    private String color;
    private Integer year;
    private String nickname;
}
