package com.Alsainey.ShimmerShine.repository;

import com.Alsainey.ShimmerShine.entities.subscription.*;
import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.entities.subscription.enums.SubscriptionStatus;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionPlanRepository planRepository;

    @Test
    void testSaveAndFindSubscription() {
        // 1. Create user
        User user = User.builder()
                .firstname("Alsainey")
                .lastname("Barry")
                .email("test@example.com")
                .password("password")
                .build();
        user = userRepository.save(user);

        // 2. Create subscription plan
        SubscriptionPlan plan = SubscriptionPlan.builder()
                .name(PlanName.BASIC)
                .maxCarWash(10)
                .description("Basic plan")
                .price(BigDecimal.valueOf(500))
                .build();
        plan = planRepository.save(plan);

        // 3. Create subscription
        Subscription subscription = Subscription.builder()
                .user(user)
                .subscriptionPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .status(SubscriptionStatus.ACTIVE)
                .remainingWashes(20)
                .build();

        subscriptionRepository.save(subscription);

        // 4. Verify count
        assertThat(subscriptionRepository.count()).isEqualTo(1);

        // 5. Fetch by user
        List<Subscription> result = subscriptionRepository.findByUserId(user.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getSubscriptionPlan().getName()).isEqualTo(PlanName.BASIC);
        assertThat(result.get(0).getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
    }
}
