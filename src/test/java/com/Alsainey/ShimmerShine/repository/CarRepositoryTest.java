package com.Alsainey.ShimmerShine.repository;

import com.Alsainey.ShimmerShine.entities.car.Car;
import com.Alsainey.ShimmerShine.repositories.car.CarRepository;
import com.Alsainey.ShimmerShine.role.Role;
import com.Alsainey.ShimmerShine.role.RoleRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User savedUser;

    @BeforeEach
    void setup() {

        Role role = roleRepository.save(
                Role.builder().name("ROLE_USER").build()
        );

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        savedUser = userRepository.save(
                User.builder()
                        .firstname("Test")
                        .lastname("User")
                        .email("test@example.com")
                        .password("password")
                        .dateOfBirth(LocalDate.of(2000, 1, 1))
                        .enabled(true)
                        .accountLocked(false)
                        .roles(roles)
                        .build()
        );
    }

    private Car buildCar(String plate, String make, String model) {
        return Car.builder()
                .user(savedUser)
                .licensePlate(plate)
                .make(make)
                .model(model)
                .year(2020)
                .nickname("TestCar")
                .build();
    }


    @Test
    void shouldCreateCar() {
        Car car = buildCar("BJL1000A", "Chevrolet", "Corvette");

        Car savedCar = carRepository.save(car);

        assertThat(savedCar.getId()).isNotNull();
        assertThat(savedCar.getLicensePlate()).isEqualTo("BJL1000A");
    }


    @Test
    void shouldFindCarById() {

        Car car = carRepository.save(buildCar("BJL2000A", "Toyota", "Camry"));

        Optional<Car> foundCar = carRepository.findById(car.getId());

        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getMake()).isEqualTo("Toyota");
    }


//    @Test
//    void shouldFindAllCars() {
//
//        carRepository.save(buildCar("BJL2001A", "Toyota", "Corolla"));
//        carRepository.save(buildCar("BJL2002A", "Honda", "Civic"));
//
//        List<Car> allCars = carRepository.findAll();
//
//        assertThat(allCars.size()).isEqualTo(2);
//    }


    @Test
    void shouldUpdateCar() {

        Car car = carRepository.save(buildCar("BJL3000A", "Ford", "Mustang"));

        car.setNickname("Beast");
        car.setMake("Ford Updated");

        Car updatedCar = carRepository.save(car);

        assertThat(updatedCar.getNickname()).isEqualTo("Beast");
        assertThat(updatedCar.getMake()).isEqualTo("Ford Updated");
    }


    @Test
    void shouldDeleteCar() {

        Car car = carRepository.save(buildCar("BJL4000A", "BMW", "M4"));

        carRepository.delete(car);

        Optional<Car> deletedCar = carRepository.findById(car.getId());

        assertThat(deletedCar).isEmpty();
    }
}
