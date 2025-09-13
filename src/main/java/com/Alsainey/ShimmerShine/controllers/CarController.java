package com.Alsainey.ShimmerShine.controllers;

import com.Alsainey.ShimmerShine.entities.Car;
import com.Alsainey.ShimmerShine.entities.dtos.CarRequest;
import com.Alsainey.ShimmerShine.entities.dtos.CarUpdateRequest;
import com.Alsainey.ShimmerShine.services.CarService;
import com.Alsainey.ShimmerShine.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Tag(name = "Car", description = "Endpoints for Car creation, updates, retrieval, and deletion")
public class CarController {

    private final CarService carService;

    @Operation(
            summary = "Add a new car for a user",
            description = "Registers a new car to the authenticated user. License plate must be unique."
    )
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addCar(@Valid @RequestBody CarRequest carRequest,
                                    @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(carService.addCar(user, carRequest));
    }


    @Operation(summary = "Update an existing car",
            description = "Updates only the fields provided in the request. Car must belong to the authenticated user.")
    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer carId,
                                         @Valid @RequestBody CarUpdateRequest carUpdateRequest,
                                         @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(carService.updateCar(carId, carUpdateRequest, user));
    }

    @Operation(summary = "Get all cars of the authenticated user")
    @GetMapping("/me")
    public ResponseEntity<List<Car>> getUserCars(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(carService.getAllCars(user));
    }

    @Operation(summary = "Get a car by its ID")
    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer carId,
                                          @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(carService.getCarById(carId, user));
    }

    @Operation(summary = "Delete a car",
            description = "Deletes a car only if it belongs to the authenticated user.")
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer carId,
                                          @AuthenticationPrincipal User user) {
        carService.deleteCar(carId, user);
        return ResponseEntity.noContent().build();
    }
}
