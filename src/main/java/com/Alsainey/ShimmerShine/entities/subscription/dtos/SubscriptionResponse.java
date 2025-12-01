package com.Alsainey.ShimmerShine.entities.subscription.dtos;

import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.entities.subscription.enums.SubscriptionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SubscriptionResponse {
    private UUID id;
    private PlanName planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status;
    private Integer remainingWashes;
}
