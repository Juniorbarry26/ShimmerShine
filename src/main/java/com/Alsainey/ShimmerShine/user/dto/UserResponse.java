package com.Alsainey.ShimmerShine.user.dto;


import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private PlanName subscriptionPlan;
    private boolean enabled;
}