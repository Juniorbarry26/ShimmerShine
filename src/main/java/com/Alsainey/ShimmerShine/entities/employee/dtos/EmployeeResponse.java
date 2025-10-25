package com.Alsainey.ShimmerShine.entities.employee.dtos;

import com.Alsainey.ShimmerShine.entities.employee.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeResponse {
    private UUID id;
    private String name;
    private String email;
    private String position;
    private String location;
    private Integer phoneNumber;
    private LocalDate startDate;
    private Status status;
    private BigDecimal salary;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
