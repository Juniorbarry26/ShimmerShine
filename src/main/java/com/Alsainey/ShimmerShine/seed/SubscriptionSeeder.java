package com.Alsainey.ShimmerShine.seed;

import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.entities.subscription.Subscription;
import com.Alsainey.ShimmerShine.entities.subscription.SubscriptionPlan;
import com.Alsainey.ShimmerShine.entities.subscription.enums.SubscriptionStatus;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionRepository;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionSeeder implements DatabaseSeeder {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public void seed() {
        if (subscriptionRepository.count() == 0) {
            log.info("Seeding subscriptions...");
            
            // Get users and plans
            User johnDoe = userRepository.findByEmail("john.doe@example.com")
                .orElseThrow(() -> new RuntimeException("John Doe user not found"));
            User janeSmith = userRepository.findByEmail("jane.smith@example.com")
                .orElseThrow(() -> new RuntimeException("Jane Smith user not found"));
            User mikeJohnson = userRepository.findByEmail("mike.johnson@example.com")
                .orElseThrow(() -> new RuntimeException("Mike Johnson user not found"));
            User sarahWilliams = userRepository.findByEmail("sarah.williams@example.com")
                .orElseThrow(() -> new RuntimeException("Sarah Williams user not found"));
            
            SubscriptionPlan basicPlan = subscriptionPlanRepository.findByName(PlanName.BASIC)
                .orElseThrow(() -> new RuntimeException("Basic plan not found"));
            SubscriptionPlan premiumPlan = subscriptionPlanRepository.findByName(PlanName.PREMIUM)
                .orElseThrow(() -> new RuntimeException("Premium plan not found"));
            
            List<Subscription> subscriptions = Arrays.asList(
                // John Doe - Basic plan (active)
                Subscription.builder()
                    .user(johnDoe)
                    .subscriptionPlan(basicPlan)
                    .startDate(LocalDate.now().minusMonths(2))
                    .endDate(LocalDate.now().plusMonths(10))
                    .status(SubscriptionStatus.ACTIVE)
                    .remainingWashes(3)
                    .build(),
                
                // Jane Smith - Premium plan (active)
                Subscription.builder()
                    .user(janeSmith)
                    .subscriptionPlan(premiumPlan)
                    .startDate(LocalDate.now().minusMonths(1))
                    .endDate(LocalDate.now().plusMonths(11))
                    .status(SubscriptionStatus.ACTIVE)
                    .remainingWashes(12)
                    .build(),
                
                // Mike Johnson - Basic plan (expired)
                Subscription.builder()
                    .user(mikeJohnson)
                    .subscriptionPlan(basicPlan)
                    .startDate(LocalDate.now().minusMonths(5))
                    .endDate(LocalDate.now().minusMonths(1))
                    .status(SubscriptionStatus.EXPIRED)
                    .remainingWashes(0)
                    .build(),
                
                // Sarah Williams - Premium plan (active)
                Subscription.builder()
                    .user(sarahWilliams)
                    .subscriptionPlan(premiumPlan)
                    .startDate(LocalDate.now().minusWeeks(2))
                    .endDate(LocalDate.now().plusMonths(11).plusWeeks(2))
                    .status(SubscriptionStatus.ACTIVE)
                    .remainingWashes(14)
                    .build()
            );
            
            subscriptionRepository.saveAll(subscriptions);
            log.info("Seeded {} subscriptions", subscriptions.size());
        } else {
            log.info("Subscriptions already exist, skipping seeding");
        }
    }
}
