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
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final UserRepository userRepository;

    public Subscription saveSubscription(User currentUser) {
        SubscriptionPlan plan = currentUser.getSubscriptionPlan();

        Subscription subscription = Subscription.builder()
                .user(currentUser)
                .subscriptionPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1)) // default 1 month
                .status(SubscriptionStatus.ACTIVE)
                .remainingWashes(plan.getMaxCarWash())
                .build();

        return subscriptionRepository.save(subscription);
    }

    public Subscription changeSubscription(User currentUser, PlanName newPlanName) {

        SubscriptionPlan newPlan = subscriptionPlanRepository.findByName(newPlanName)
                .orElseThrow(() -> new RuntimeException("Subscription plan not found"));


        Subscription activeSubscription = subscriptionRepository.findActiveSubscriptionByUser(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("No active subscription found"));


        activeSubscription.setSubscriptionPlan(newPlan);
        activeSubscription.setRemainingWashes(newPlan.getMaxCarWash());
        activeSubscription.setStartDate(LocalDate.now());
        activeSubscription.setEndDate(LocalDate.now().plusMonths(1));
        activeSubscription.setStatus(SubscriptionStatus.ACTIVE);

        // 4️⃣ Save and return
        return subscriptionRepository.save(activeSubscription);
    }

//
//    // Use a car wash
//    public void useCarWash(User existingUser) {
//        Subscription subscription = subscriptionRepository.findActiveByUserId(existingUser.getId())
//                .orElseThrow(() -> new RuntimeException("No active subscription"));
//
//        if (subscription.getRemainingWashes() <= 0 && subscription.getSubscriptionPlan().getName() != PlanName.ULTIMATE) {
//            throw new RuntimeException("No washes remaining");
//        }
//
//        // Only decrement if not unlimited
//        if (subscription.getSubscriptionPlan().getName() != PlanName.ULTIMATE) {
//            subscription.setRemainingWashes(subscription.getRemainingWashes() - 1);
//
//            if (subscription.getRemainingWashes() == 0) {
//                subscription.setStatus(SubscriptionStatus.EXPIRED);
//            }
//        }
//
//        subscriptionRepository.save(subscription);
//    }
//
//    // Expire old subscriptions automatically (runs every midnight)
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void expireSubscriptions() {
//        List<Subscription> activeSubs = subscriptionRepository.findAllActive();
//        LocalDate today = LocalDate.now();
//
//        for (Subscription sub : activeSubs) {
//            if (sub.getEndDate().isBefore(today)) {
//                sub.setStatus(SubscriptionStatus.EXPIRED);
//                subscriptionRepository.save(sub);
//            }
//        }
//    }
}
