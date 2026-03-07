package com.Alsainey.ShimmerShine.seed;

import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.entities.subscription.SubscriptionPlan;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionPlanSeeder implements DatabaseSeeder {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public void seed() {
        if (subscriptionPlanRepository.count() == 0) {
            log.info("Seeding subscription plans...");
            
            List<SubscriptionPlan> plans = Arrays.asList(
                SubscriptionPlan.builder()
                    .name(PlanName.BASIC)
                    .maxCarWash(5)
                    .description("Basic plan with 5 car washes per month")
                    .price(new BigDecimal("29.99"))
                    .build(),
                SubscriptionPlan.builder()
                    .name(PlanName.PREMIUM)
                    .maxCarWash(15)
                    .description("Premium plan with 15 car washes per month")
                    .price(new BigDecimal("79.99"))
                    .build(),
                SubscriptionPlan.builder()
                    .name(PlanName.ULTIMATE)
                    .maxCarWash(-1) // Unlimited
                    .description("Ultimate plan with unlimited car washes")
                    .price(new BigDecimal("149.99"))
                    .build()
            );
            
            subscriptionPlanRepository.saveAll(plans);
            log.info("Seeded {} subscription plans", plans.size());
        } else {
            log.info("Subscription plans already exist, skipping seeding");
        }
    }
}
