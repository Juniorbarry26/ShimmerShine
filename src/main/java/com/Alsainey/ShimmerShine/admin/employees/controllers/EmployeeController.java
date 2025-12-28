package com.Alsainey.ShimmerShine.admin.employees.controllers;

import com.Alsainey.ShimmerShine.admin.employees.entities.dtos.EmployeeRequest;
import com.Alsainey.ShimmerShine.admin.employees.services.EmployeeService;
import com.Alsainey.ShimmerShine.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/employees")
@Tag(name = "Employee", description = "Endpoints for Employee creation, updates, retrieval, and deletion")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    @Operation(
            summary = "Add a new employee",
            description = "Admin will be adding an employee"
    )
    public ResponseEntity<?> createEmployee(
            @RequestBody @Valid EmployeeRequest request,
            @AuthenticationPrincipal User authenticatedUser
            )  {
       return ResponseEntity.ok(employeeService.createEmployee(request, authenticatedUser));
    }

}
