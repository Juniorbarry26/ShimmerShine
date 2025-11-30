package com.Alsainey.ShimmerShine.services.subscription;

import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanRepository planRepository;
    private final UserRepository userRepository;

    // to change a subscription, use the subscription plan from the user when creating an account
    public User changeSubscription(User currentUser, PlanName newPlanName) {
        currentUser.setSubscriptionPlan(newPlanName);

        return userRepository.save(currentUser);
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
