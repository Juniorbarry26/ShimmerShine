package com.Alsainey.ShimmerShine.admin.employees.entities.dtos;
import com.Alsainey.ShimmerShine.admin.employees.entities.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeUpdate {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Position cannot be blank")
    private String position;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Positive(message = "Phone number must be positive")
    private String phoneNumber;

    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;

    private Status status;

    @Positive(message = "Salary must be a positive amount")
    private BigDecimal salary;
}
