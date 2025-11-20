package com.Alsainey.ShimmerShine.repository;

import com.Alsainey.ShimmerShine.entities.subscription.Subscription;
import com.Alsainey.ShimmerShine.entities.subscription.SubscriptionPlan;
import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.entities.subscription.enums.SubscriptionStatus;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionPlanRepository planRepository;


    private User createUser() {
        User user = User.builder()
                .firstname("Alsainey")
                .lastname("Barry")
                .email("test@example.com")
                .password("password")
                .build();
        return userRepository.save(user);
    }

    private SubscriptionPlan createPlan() {
        SubscriptionPlan plan = SubscriptionPlan.builder()
                .name(PlanName.BASIC)
                .maxCarWash(10)
                .description("Basic plan")
                .price(BigDecimal.valueOf(500))
                .build();
        return planRepository.save(plan);
    }



    @Test
    void shouldSaveAndFindSubscription() {

        User user = createUser();
        SubscriptionPlan plan = createPlan();

        Subscription sub = Subscription.builder()
                .user(user)
                .subscriptionPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .status(SubscriptionStatus.ACTIVE)
                .remainingWashes(20)
                .build();

        subscriptionRepository.save(sub);

        List<Subscription> found = subscriptionRepository.findByUserId(user.getId());

        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
        assertThat(found.get(0).getSubscriptionPlan().getName()).isEqualTo(PlanName.BASIC);
    }



    @Test
    void shouldUpdateSubscription() {
        User user = createUser();
        SubscriptionPlan plan = createPlan();

        Subscription sub = Subscription.builder()
                .user(user)
                .subscriptionPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .status(SubscriptionStatus.ACTIVE)
                .remainingWashes(10)
                .build();

        Subscription saved = subscriptionRepository.save(sub);

        saved.setRemainingWashes(5);
        saved.setStatus(SubscriptionStatus.EXPIRED);

        Subscription updated = subscriptionRepository.save(saved);

        assertThat(updated.getRemainingWashes()).isEqualTo(5);
        assertThat(updated.getStatus()).isEqualTo(SubscriptionStatus.EXPIRED);
    }


    @Test
    void shouldDeleteSubscription() {

        User user = createUser();
        SubscriptionPlan plan = createPlan();

        Subscription sub = Subscription.builder()
                .user(user)
                .subscriptionPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .status(SubscriptionStatus.ACTIVE)
                .remainingWashes(20)
                .build();

        Subscription saved = subscriptionRepository.save(sub);

        subscriptionRepository.deleteById(saved.getId());

        Optional<Subscription> deleted = subscriptionRepository.findById(saved.getId());

        assertThat(deleted).isEmpty();
    }



    @Test
    void shouldCountSubscriptions() {

        User user = createUser();
        SubscriptionPlan plan = createPlan();

        Subscription s1 = Subscription.builder()
                .user(user)
                .subscriptionPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .status(SubscriptionStatus.ACTIVE)
                .remainingWashes(20)
                .build();

        Subscription s2 = Subscription.builder()
                .user(user)
                .subscriptionPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .status(SubscriptionStatus.ACTIVE)
                .remainingWashes(15)
                .build();

        subscriptionRepository.save(s1);
        subscriptionRepository.save(s2);

        assertThat(subscriptionRepository.count()).isEqualTo(2);
    }
}
