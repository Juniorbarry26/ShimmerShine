package com.Alsainey.ShimmerShine.repositories;

import com.Alsainey.ShimmerShine.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    // Find a car by its license plate
    Optional<Car> findByLicensePlate(String licensePlate);

    // Find all cars belonging to a specific user
    List<Car> findAllByUserId(Integer userId);
}
