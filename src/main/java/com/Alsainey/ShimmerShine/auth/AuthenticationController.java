package com.Alsainey.ShimmerShine.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration, login, and account activation")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(
            summary = "Register a new user",
            description = "Creates a new account and sends an activation email. " +
                    "User must confirm their email before accessing protected resources."
    )
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    @Operation(
            summary = "Authenticate user (login)",
            description = "Authenticates a user using email and password. " +
                    "Returns a JWT access token if successful."
    )
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/activate-account")
    @Operation(
            summary = "Activate account",
            description = "Activates a newly registered user account using the token sent via email."
    )
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        authenticationService.activateAccount(token);
    }

    // Example of a secured endpoint (if you add one later)
    @GetMapping("/me")
    @Operation(
            summary = "Get current user info",
            description = "Fetch details of the currently authenticated user.",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("This is a secured endpoint (user details).");
    }
}
