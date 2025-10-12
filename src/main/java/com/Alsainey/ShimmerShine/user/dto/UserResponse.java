package com.Alsainey.ShimmerShine.user.dto;


import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponse {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private PlanName subscriptionPlan;
    private boolean enabled;
}