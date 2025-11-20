package com.Alsainey.ShimmerShine.repository;

import com.Alsainey.ShimmerShine.role.Role;
import com.Alsainey.ShimmerShine.role.RoleRepository;
import com.Alsainey.ShimmerShine.user.Token;
import com.Alsainey.ShimmerShine.user.TokenRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    // Utility to create a user with role
    private User createUser() {
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

        return userRepository.save(user);
    }

    @Test
    void shouldSaveToken() {
        User savedUser = createUser();

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

    @Test
    void shouldFindTokenById() {
        User savedUser = createUser();

        Token token = Token.builder()
                .token("lookup-token")
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .user(savedUser)
                .build();

        Token savedToken = tokenRepository.save(token);

        Optional<Token> found = tokenRepository.findById(savedToken.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getToken()).isEqualTo("lookup-token");
    }

    @Test
    void shouldFindAllTokens() {
        User savedUser = createUser();

        Token t1 = Token.builder()
                .token("t1")
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .user(savedUser)
                .build();

        Token t2 = Token.builder()
                .token("t2")
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .user(savedUser)
                .build();

        tokenRepository.save(t1);
        tokenRepository.save(t2);

        List<Token> tokens = tokenRepository.findAll();

        assertThat(tokens).hasSize(2);
    }

    @Test
    void shouldUpdateToken() {
        User savedUser = createUser();

        Token token = Token.builder()
                .token("old-token")
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .user(savedUser)
                .build();

        Token savedToken = tokenRepository.save(token);

        savedToken.setToken("updated-token");
        Token updatedToken = tokenRepository.save(savedToken);

        assertThat(updatedToken.getToken()).isEqualTo("updated-token");
    }

    @Test
    void shouldDeleteToken() {
        User savedUser = createUser();

        Token token = Token.builder()
                .token("delete-token")
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .user(savedUser)
                .build();

        Token savedToken = tokenRepository.save(token);

        tokenRepository.deleteById(savedToken.getId());

        Optional<Token> found = tokenRepository.findById(savedToken.getId());

        assertThat(found).isEmpty();
    }
}
