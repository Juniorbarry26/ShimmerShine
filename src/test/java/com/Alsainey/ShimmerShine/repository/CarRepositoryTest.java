package com.Alsainey.ShimmerShine.repository;

import com.Alsainey.ShimmerShine.entities.car.Car;
import com.Alsainey.ShimmerShine.repositories.car.CarRepository;
import com.Alsainey.ShimmerShine.role.Role;
import com.Alsainey.ShimmerShine.role.RoleRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

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

    @Test
    void shouldSaveCar() {

        Role role = Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(role);

        User user = User.builder()
                .firstname("Test")
                .lastname("User")
                .email("test@example.com")
                .password("password")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .enabled(true)
                .accountLocked(false)
                .roles(List.of(role))
                .build();

        User savedUser = userRepository.save(user);

        Car car = Car.builder()
                .user(savedUser)
                .licensePlate("BJL2002A")
                .make("Chevrolet")
                .model("Corvette")
                .year(2020)
                .nickname("Demon")
                .build();

        Car savedCar = carRepository.save(car);

        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getId()).isNotNull();
        assertThat(savedCar.getLicensePlate()).isEqualTo("BJL2002A");
        assertThat(savedCar.getUser().getEmail()).isEqualTo("test@example.com");
    }
}
