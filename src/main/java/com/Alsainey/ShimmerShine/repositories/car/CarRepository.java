package com.Alsainey.ShimmerShine.repositories.car;

import com.Alsainey.ShimmerShine.entities.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    // Find a car by its license plate
    Optional<Car> findByLicensePlate(String licensePlate);

    // Find all cars belonging to a specific user
    List<Car> findAllByUserId(UUID userId);
}
