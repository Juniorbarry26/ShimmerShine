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
class SubscriptionPlanRepositoryTest {

    @Autowired
    private SubscriptionPlanRepository planRepository;

    @Test
    void testSaveAndFindSubscriptionPlan() {

        SubscriptionPlan plan = SubscriptionPlan.builder()
                .name(PlanName.PREMIUM)
                .maxCarWash(20)
                .description("Premium monthly car wash plan")
                .price(BigDecimal.valueOf(1000))
                .build();

        planRepository.save(plan);

        Optional<SubscriptionPlan> result = planRepository.findByName(PlanName.PREMIUM);

        assertThat(result).isPresent();
        assertThat(result.get().getPrice()).isEqualTo(BigDecimal.valueOf(1000));
        assertThat(result.get().getMaxCarWash()).isEqualTo(20);
        assertThat(result.get().getCreatedAt()).isNotNull();
    }



    @Test
    void testUniquePlanName() {

        SubscriptionPlan plan1 = SubscriptionPlan.builder()
                .name(PlanName.BASIC)
                .maxCarWash(10)
                .description("Basic plan")
                .price(BigDecimal.valueOf(500))
                .build();

        planRepository.save(plan1);

        SubscriptionPlan plan2 = SubscriptionPlan.builder()
                .name(PlanName.BASIC)
                .maxCarWash(15)
                .description("Duplicate plan")
                .price(BigDecimal.valueOf(700))
                .build();

        Exception thrown = null;

        try {
            planRepository.saveAndFlush(plan2);
        } catch (Exception e) {
            thrown = e;
        }

        assertThat(thrown).isNotNull();
    }



    @Test
    void testUpdateSubscriptionPlan() {

        SubscriptionPlan plan = SubscriptionPlan.builder()
                .name(PlanName.BASIC)
                .maxCarWash(30)
                .description("Pro plan")
                .price(BigDecimal.valueOf(1500))
                .build();

        SubscriptionPlan saved = planRepository.save(plan);

        saved.setPrice(BigDecimal.valueOf(2000));
        saved.setMaxCarWash(40);

        SubscriptionPlan updated = planRepository.save(saved);

        assertThat(updated.getPrice()).isEqualTo(BigDecimal.valueOf(2000));
        assertThat(updated.getMaxCarWash()).isEqualTo(40);
    }



    @Test
    void testDeleteSubscriptionPlan() {

        SubscriptionPlan plan = SubscriptionPlan.builder()
                .name(PlanName.ULTIMATE)
                .maxCarWash(50)
                .description("Ultimate plan")
                .price(BigDecimal.valueOf(2500))
                .build();

        SubscriptionPlan saved = planRepository.save(plan);

        planRepository.deleteById(saved.getId());

        Optional<SubscriptionPlan> deleted = planRepository.findById(saved.getId());

        assertThat(deleted).isEmpty();
    }


    @Test
    void testCountPlans() {

        SubscriptionPlan p1 = SubscriptionPlan.builder()
                .name(PlanName.BASIC)
                .maxCarWash(10)
                .price(BigDecimal.valueOf(500))
                .build();

        SubscriptionPlan p2 = SubscriptionPlan.builder()
                .name(PlanName.PREMIUM)
                .maxCarWash(20)
                .price(BigDecimal.valueOf(1000))
                .build();

        planRepository.save(p1);
        planRepository.save(p2);

        assertThat(planRepository.count()).isEqualTo(2);
    }
}
