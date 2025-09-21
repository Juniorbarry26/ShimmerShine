package com.Alsainey.ShimmerShine.entities.car.dtos;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarResponse {
    private Integer id;
    private String licensePlate;
    private String make;
    private String model;
    private String color;
    private Integer year;
    private String nickname;
    private Integer userId; // link back to owner
}
