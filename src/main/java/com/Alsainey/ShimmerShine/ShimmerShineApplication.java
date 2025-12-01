package com.Alsainey.ShimmerShine;

import com.Alsainey.ShimmerShine.entities.subscription.SubscriptionPlan;
import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import com.Alsainey.ShimmerShine.role.Role;
import com.Alsainey.ShimmerShine.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.math.BigDecimal;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
 public class ShimmerShineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShimmerShineApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(Role.builder().name("USER").build());
			}
		};

	}

	@Bean
	public CommandLineRunner seedPlans(SubscriptionPlanRepository planRepository) {
		return args -> {
			if (planRepository.findByName(PlanName.BASIC).isEmpty()) {
				planRepository.save(SubscriptionPlan.builder()
						.name(PlanName.BASIC)
						.maxCarWash(5)
						.price(BigDecimal.valueOf(50))
						.description("Basic plan")
						.build());
			}

			if (planRepository.findByName(PlanName.PREMIUM).isEmpty()) {
				planRepository.save(SubscriptionPlan.builder()
						.name(PlanName.PREMIUM)
						.maxCarWash(10)
						.price(BigDecimal.valueOf(100))
						.description("Premium plan")
						.build());
			}

			if (planRepository.findByName(PlanName.ULTIMATE).isEmpty()) {
				planRepository.save(SubscriptionPlan.builder()
						.name(PlanName.ULTIMATE)
						.maxCarWash(20)
						.price(BigDecimal.valueOf(200))
						.description("Ultimate plan")
						.build());
			}
		};
	}

}
