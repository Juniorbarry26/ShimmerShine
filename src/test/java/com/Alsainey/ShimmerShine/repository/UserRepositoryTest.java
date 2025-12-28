package com.Alsainey.ShimmerShine.repository;

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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Role role;
    private User user;

    @BeforeEach
    void setUp() {
        // Create and save a role
        role = Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(role);

        // Create a user with a mutable list for roles
        user = User.builder()
                .firstname("Alsainey")
                .lastname("Barry")
                .email("test@example.com")
                .password("password")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .enabled(true)
                .accountLocked(false)
                .build();

        user.setRoles(new ArrayList<>());
        user.getRoles().add(role); // mutable list
    }

    @Test
    void shouldSaveUser() {
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldFindUserById() {
        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldFindAllUsers() {
        userRepository.save(user);

        List<User> users = userRepository.findAll();

        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    void shouldUpdateUser() {
        User savedUser = userRepository.save(user);

        User userToUpdate = userRepository.findById(savedUser.getId()).orElseThrow();

        userToUpdate.setFirstname("UpdatedName");
        userToUpdate.setEmail("updated@example.com");

        User updatedUser = userRepository.save(userToUpdate);

        assertThat(updatedUser.getFirstname()).isEqualTo("UpdatedName");
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");

        User verifyUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertThat(verifyUser.getFirstname()).isEqualTo("UpdatedName");
        assertThat(verifyUser.getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    void shouldDeleteUser() {
        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser);

        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isEmpty();
    }

    @Test
    void shouldFindUserByEmail() {
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getFirstname()).isEqualTo("Alsainey");
    }
}
