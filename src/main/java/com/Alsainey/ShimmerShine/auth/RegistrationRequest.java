package com.Alsainey.ShimmerShine.auth;

import com.Alsainey.ShimmerShine.entities.subscription.Subscription;
import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegistrationRequest {
    @NotBlank(message = "Firstname is mandatory")
    @NotEmpty(message = "Firstname is mandatory")
    private String firstname;
    @NotBlank(message = "Lastname is mandatory")
    @NotEmpty(message = "Lastname is mandatory")
    private String lastname;
    @NotBlank(message = "Email is mandatory")
    @NotEmpty(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be between 8 character long minimum")
    private String password;

    @NotNull(message = "Subscription plan is required")
    private PlanName subscriptionPlan;
}
