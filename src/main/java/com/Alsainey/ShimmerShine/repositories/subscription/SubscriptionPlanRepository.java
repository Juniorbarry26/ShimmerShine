package com.Alsainey.ShimmerShine.repositories.subscription;

import com.Alsainey.ShimmerShine.entities.subscription.SubscriptionPlan;
import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    Optional<SubscriptionPlan> findByName(PlanName name);
}