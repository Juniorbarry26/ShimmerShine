package com.Alsainey.ShimmerShine.seed;

import com.Alsainey.ShimmerShine.admin.employees.entities.Employee;
import com.Alsainey.ShimmerShine.admin.employees.entities.Status;
import com.Alsainey.ShimmerShine.admin.employees.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeSeeder implements DatabaseSeeder {

    private final EmployeeRepository employeeRepository;

    @Override
    public void seed() {
        if (employeeRepository.count() == 0) {
            log.info("Seeding employees...");
            
            List<Employee> employees = Arrays.asList(
                Employee.builder()
                    .name("Robert Chen")
                    .email("robert.chen@shimmershine.com")
                    .position("Senior Car Wash Specialist")
                    .location("Main Branch")
                    .phoneNumber("+1234567895")
                    .startDate(LocalDate.of(2022, 1, 15))
                    .status(Status.ACTIVE)
                    .salary(new BigDecimal("45000.00"))
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build(),
                
                Employee.builder()
                    .name("Maria Rodriguez")
                    .email("maria.rodriguez@shimmershine.com")
                    .position("Car Wash Technician")
                    .location("Main Branch")
                    .phoneNumber("+1234567896")
                    .startDate(LocalDate.of(2023, 3, 10))
                    .status(Status.ACTIVE)
                    .salary(new BigDecimal("35000.00"))
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build(),
                
                Employee.builder()
                    .name("James Wilson")
                    .email("james.wilson@shimmershine.com")
                    .position("Trainee")
                    .location("Main Branch")
                    .phoneNumber("+1234567897")
                    .startDate(LocalDate.of(2024, 6, 1))
                    .status(Status.TRAINING)
                    .salary(new BigDecimal("28000.00"))
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build(),
                
                Employee.builder()
                    .name("Lisa Thompson")
                    .email("lisa.thompson@shimmershine.com")
                    .position("Customer Service Manager")
                    .location("Main Branch")
                    .phoneNumber("+1234567898")
                    .startDate(LocalDate.of(2021, 9, 20))
                    .status(Status.ON_LEAVE)
                    .salary(new BigDecimal("52000.00"))
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build(),
                
                Employee.builder()
                    .name("David Kim")
                    .email("david.kim@shimmershine.com")
                    .position("Detailing Specialist")
                    .location("Main Branch")
                    .phoneNumber("+1234567899")
                    .startDate(LocalDate.of(2022, 11, 5))
                    .status(Status.ACTIVE)
                    .salary(new BigDecimal("42000.00"))
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build()
            );
            
            employeeRepository.saveAll(employees);
            log.info("Seeded {} employees", employees.size());
        } else {
            log.info("Employees already exist, skipping seeding");
        }
    }
}
