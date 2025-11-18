package com.Alsainey.ShimmerShine.repository;

import com.Alsainey.ShimmerShine.entities.subscription.SubscriptionPlan;

import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SubscriptionPlanRepositoryTest {

    @Autowired
    private SubscriptionPlanRepository planRepository;

    @Test
    void testSaveAndFindSubscriptionPlan() {
        // Arrange
        SubscriptionPlan plan = SubscriptionPlan.builder()
                .name(PlanName.PREMIUM)
                .maxCarWash(20)
                .description("Premium monthly car wash plan")
                .price(BigDecimal.valueOf(1000))
                .build();

        planRepository.save(plan);

        // Act
        Optional<SubscriptionPlan> result = planRepository.findByName(PlanName.PREMIUM);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getPrice()).isEqualTo(BigDecimal.valueOf(1000));
        assertThat(result.get().getMaxCarWash()).isEqualTo(20);
        assertThat(result.get().getCreatedAt()).isNotNull();
    }

    @Test
    void testUniquePlanName() {
        // First plan
        SubscriptionPlan plan1 = SubscriptionPlan.builder()
                .name(PlanName.BASIC)
                .maxCarWash(10)
                .description("Basic plan")
                .price(BigDecimal.valueOf(500))
                .build();

        planRepository.save(plan1);

        // Second plan (same name → should fail)
        SubscriptionPlan plan2 = SubscriptionPlan.builder()
                .name(PlanName.BASIC)
                .maxCarWash(15)
                .description("Duplicate plan should not pass")
                .price(BigDecimal.valueOf(700))
                .build();

        // Assert unique constraint works
        Throwable thrown = null;
        try {
            planRepository.saveAndFlush(plan2);
        } catch (Exception e) {
            thrown = e;
        }

        assertThat(thrown).isNotNull();
    }
}
