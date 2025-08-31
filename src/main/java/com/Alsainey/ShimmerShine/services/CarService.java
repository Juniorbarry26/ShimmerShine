package com.Alsainey.ShimmerShine.services;

import com.Alsainey.ShimmerShine.entities.Car;
import com.Alsainey.ShimmerShine.entities.dtos.CarRequest;
import com.Alsainey.ShimmerShine.entities.dtos.CarUpdateRequest;
import com.Alsainey.ShimmerShine.repositories.CarRepository;
import com.Alsainey.ShimmerShine.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    // Create a new car
    public Car addCar(User currentUser, CarRequest carRequest) {
        Optional<Car> existingCar = carRepository.findByLicensePlate(carRequest.getLicensePlate());

        if (existingCar.isPresent()) {
            Car car = existingCar.get();
            if (car.getUser() != null && !car.getUser().getId().equals(currentUser.getId())) {
                throw new IllegalArgumentException("Car is already assigned to another user!");
            } else if (car.getUser() != null && car.getUser().getId().equals(currentUser.getId())) {
                throw new IllegalArgumentException("You already own this car!");
            }

        }

        Car newCar = Car.builder()
                .licensePlate(carRequest.getLicensePlate())
                .make(carRequest.getMake())
                .model(carRequest.getModel())
                .color(carRequest.getColor())
                .year(carRequest.getYear())
                .nickname(carRequest.getNickname())
                .user(currentUser)
                .build();

        if (newCar.getYear() > 4) {
            throw new IllegalArgumentException("Year must be a 4-digit number");
        }
        return carRepository.save(newCar);
    }

    // Update an existing car
    public Car updateCar(Integer carId, CarUpdateRequest carUpdateRequest, User currentUser) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found!"));

        if (!car.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You cannot update a car that does not belong to you!");
        }

        // Update fields only if provided
        Optional.ofNullable(carUpdateRequest.getMake()).ifPresent(car::setMake);
        Optional.ofNullable(carUpdateRequest.getModel()).ifPresent(car::setModel);
        Optional.ofNullable(carUpdateRequest.getColor()).ifPresent(car::setColor);
        Optional.ofNullable(carUpdateRequest.getYear()).ifPresent(car::setYear);
        Optional.ofNullable(carUpdateRequest.getNickname()).ifPresent(car::setNickname);
        Optional.ofNullable(currentUser.getUsername());

        return carRepository.save(car);
    }

    // Get a car by ID
    public Car getCarById(Integer carId, User currentUser) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found!"));

        if (!car.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You cannot access a car that does not belong to you!");
        }

        return car;
    }

    // Get all cars for current user
    public List<Car> getAllCars(User currentUser) {
        return carRepository.findAllByUserId(currentUser.getId());
    }

    // Delete a car
    public void deleteCar(Integer carId, User currentUser) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found!"));

        if (!car.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You cannot delete a car that does not belong to you!");
        }

        carRepository.delete(car);
    }
}
