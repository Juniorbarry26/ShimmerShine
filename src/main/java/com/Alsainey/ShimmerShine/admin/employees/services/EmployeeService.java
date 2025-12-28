package com.Alsainey.ShimmerShine.admin.employees.services;

import com.Alsainey.ShimmerShine.admin.employees.entities.Employee;
import com.Alsainey.ShimmerShine.admin.employees.entities.dtos.EmployeeRequest;
import com.Alsainey.ShimmerShine.admin.employees.repositories.EmployeeRepository;
import com.Alsainey.ShimmerShine.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeRequest request, User adminUser) {

        Optional<Employee> existingByEmail = employeeRepository.findByEmail(request.getEmail());
        if (existingByEmail.isPresent()) {
            throw new IllegalArgumentException("Employee with this email already exists.");
        }

        Optional<Employee> existingByPhone = employeeRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existingByPhone.isPresent()) {
            throw new IllegalArgumentException("Employee with this phone number already exists.");
        }

        Employee employee = Employee
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .position(request.getPosition())
                .location(request.getLocation())
                .phoneNumber(request.getPhoneNumber())
                .startDate(request.getStartDate())
                .status(request.getStatus())
                .salary(request.getSalary())
                .build();

        return employeeRepository.save(employee);
    }
}
