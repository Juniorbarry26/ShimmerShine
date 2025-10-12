package com.Alsainey.ShimmerShine.auth;

import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.role.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class UserAuthResponse {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private PlanName subscriptionPlan;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
