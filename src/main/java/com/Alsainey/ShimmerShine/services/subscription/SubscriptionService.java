package com.Alsainey.ShimmerShine.services.subscription;

import com.Alsainey.ShimmerShine.entities.subscription.Subscription;
import com.Alsainey.ShimmerShine.entities.subscription.SubscriptionPlan;
import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.entities.subscription.enums.SubscriptionStatus;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanRepository planRepository;
    private final UserRepository userRepository;

    // Create subscription (at signup or plan change)
    public Subscription createSubscription(User currentUser, PlanName planName) {

        // Try to find a plan, or create it dynamically if it doesn't exist
        SubscriptionPlan plan = planRepository.findByName(planName)
                .orElseGet(() -> {
                    SubscriptionPlan newPlan = new SubscriptionPlan();
                    newPlan.setName(planName);

                    switch (planName) {
                        case BASIC -> newPlan.setMaxCarWash(4);
                        case PREMIUM -> newPlan.setMaxCarWash(10);
                        case ULTIMATE -> newPlan.setMaxCarWash(Integer.MAX_VALUE);
                    }

                    return planRepository.save(newPlan);
                });

        int remainingWashes = planName == PlanName.ULTIMATE ? Integer.MAX_VALUE : plan.getMaxCarWash();

        Subscription subscription = Subscription.builder()
                .user(currentUser)
                .subscriptionPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .status(SubscriptionStatus.ACTIVE)
                .remainingWashes(remainingWashes)
                .build();

        return subscriptionRepository.save(subscription);
    }

    // Change subscription plan (upgrade/downgrade)
    public Subscription changeSubscription(User currentUser, PlanName newPlanName) {
        Subscription current = subscriptionRepository.findActiveByUserId(currentUser)
                .orElseThrow(() -> new RuntimeException("No active subscription"));

        // Mark current subscription as canceled
        current.setStatus(SubscriptionStatus.CANCELED);
        subscriptionRepository.save(current);

        // Create a new subscription, dynamically creating plan if missing
        return createSubscription(currentUser, newPlanName);
    }

    // Cancel subscription manually
    public void cancelSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscriptionRepository.save(subscription);
    }

    // Use a car wash
    public void useCarWash(User existingUser) {
        Subscription subscription = subscriptionRepository.findActiveByUserId(existingUser)
                .orElseThrow(() -> new RuntimeException("No active subscription"));

        if (subscription.getRemainingWashes() <= 0 && subscription.getSubscriptionPlan().getName() != PlanName.ULTIMATE) {
            throw new RuntimeException("No washes remaining");
        }

        // Only decrement if not unlimited
        if (subscription.getSubscriptionPlan().getName() != PlanName.ULTIMATE) {
            subscription.setRemainingWashes(subscription.getRemainingWashes() - 1);

            if (subscription.getRemainingWashes() == 0) {
                subscription.setStatus(SubscriptionStatus.EXPIRED);
            }
        }

        subscriptionRepository.save(subscription);
    }

    // Expire old subscriptions automatically (runs every midnight)
    @Scheduled(cron = "0 0 0 * * ?")
    public void expireSubscriptions() {
        List<Subscription> activeSubs = subscriptionRepository.findAllActive();
        LocalDate today = LocalDate.now();

        for (Subscription sub : activeSubs) {
            if (sub.getEndDate().isBefore(today)) {
                sub.setStatus(SubscriptionStatus.EXPIRED);
                subscriptionRepository.save(sub);
            }
        }
    }
}
