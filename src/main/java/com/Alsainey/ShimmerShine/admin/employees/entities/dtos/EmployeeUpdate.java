package com.Alsainey.ShimmerShine.entities.employee.dtos;
import com.Alsainey.ShimmerShine.entities.employee.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeUpdate {
    private String name;
    private String position;
    private String location;
    private Integer phoneNumber;
    private LocalDate startDate;
    private Status status;
    private BigDecimal salary;
}