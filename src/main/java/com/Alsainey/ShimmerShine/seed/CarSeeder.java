package com.Alsainey.ShimmerShine.seed;

import com.Alsainey.ShimmerShine.entities.car.Car;
import com.Alsainey.ShimmerShine.repositories.car.CarRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CarSeeder implements DatabaseSeeder {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Override
    public void seed() {
        if (carRepository.count() == 0) {
            log.info("Seeding cars...");
            
            // Get users to assign cars to
            User johnDoe = userRepository.findByEmail("john.doe@example.com")
                .orElseThrow(() -> new RuntimeException("John Doe user not found"));
            User janeSmith = userRepository.findByEmail("jane.smith@example.com")
                .orElseThrow(() -> new RuntimeException("Jane Smith user not found"));
            User mikeJohnson = userRepository.findByEmail("mike.johnson@example.com")
                .orElseThrow(() -> new RuntimeException("Mike Johnson user not found"));
            User sarahWilliams = userRepository.findByEmail("sarah.williams@example.com")
                .orElseThrow(() -> new RuntimeException("Sarah Williams user not found"));
            
            List<Car> cars = Arrays.asList(
                // John Doe's cars
                Car.builder()
                    .user(johnDoe)
                    .licensePlate("ABC123")
                    .make("Toyota")
                    .model("Camry")
                    .color("Silver")
                    .year(2020)
                    .nickname("Silver Bullet")
                    .build(),
                
                Car.builder()
                    .user(johnDoe)
                    .licensePlate("XYZ789")
                    .make("Honda")
                    .model("CR-V")
                    .color("Blue")
                    .year(2022)
                    .nickname("Blue Beast")
                    .build(),
                
                // Jane Smith's car
                Car.builder()
                    .user(janeSmith)
                    .licensePlate("DEF456")
                    .make("Tesla")
                    .model("Model 3")
                    .color("Red")
                    .year(2023)
                    .nickname("Electric Dream")
                    .build(),
                
                // Mike Johnson's cars
                Car.builder()
                    .user(mikeJohnson)
                    .licensePlate("GHI012")
                    .make("Ford")
                    .model("F-150")
                    .color("Black")
                    .year(2021)
                    .nickname("Black Beauty")
                    .build(),
                
                Car.builder()
                    .user(mikeJohnson)
                    .licensePlate("JKL345")
                    .make("BMW")
                    .model("X5")
                    .color("White")
                    .year(2022)
                    .nickname("White Lightning")
                    .build(),
                
                // Sarah Williams' car
                Car.builder()
                    .user(sarahWilliams)
                    .licensePlate("MNO678")
                    .make("Mercedes-Benz")
                    .model("C-Class")
                    .color("Gray")
                    .year(2023)
                    .nickname("Gray Ghost")
                    .build()
            );
            
            carRepository.saveAll(cars);
            log.info("Seeded {} cars", cars.size());
        } else {
            log.info("Cars already exist, skipping seeding");
        }
    }
}
