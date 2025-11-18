package com.Alsainey.ShimmerShine.repository;

import com.Alsainey.ShimmerShine.user.Token;
import com.Alsainey.ShimmerShine.user.TokenRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import com.Alsainey.ShimmerShine.role.Role;
import com.Alsainey.ShimmerShine.role.RoleRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldSaveToken() {


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

        Token token = Token.builder()
                .token("sample-token-123")
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .validatedAt(LocalDateTime.now())
                .user(savedUser)
                .build();

        Token savedToken = tokenRepository.save(token);

        assertThat(savedToken).isNotNull();
        assertThat(savedToken.getId()).isNotNull();
        assertThat(savedToken.getUser().getEmail()).isEqualTo("test@example.com");
    }
}
