package com.Alsainey.ShimmerShine.repositories.employee;

import com.Alsainey.ShimmerShine.entities.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
