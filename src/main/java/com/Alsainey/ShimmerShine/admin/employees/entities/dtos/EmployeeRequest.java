package com.Alsainey.ShimmerShine.admin.employees.entities.dtos;

import com.Alsainey.ShimmerShine.admin.employees.entities.Status;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeRequest {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Position is required")
    @NotBlank(message = "Position cannot be blank")
    private String position;

    @NotNull(message = "Location is required")
    @NotBlank(message = "Location cannot be blank")
    private String location;

    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be a positive amount")
    private BigDecimal salary;
}
