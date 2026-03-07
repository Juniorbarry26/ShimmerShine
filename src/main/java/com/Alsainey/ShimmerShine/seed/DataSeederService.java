package com.Alsainey.ShimmerShine.seed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class DataSeederService implements CommandLineRunner {

    private final RoleSeeder roleSeeder;
    private final SubscriptionPlanSeeder subscriptionPlanSeeder;
    private final UserSeeder userSeeder;
    private final EmployeeSeeder employeeSeeder;
    private final CarSeeder carSeeder;
    private final SubscriptionSeeder subscriptionSeeder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting database seeding...");
        
        try {
            // Seed in order of dependencies
            roleSeeder.seed();
            subscriptionPlanSeeder.seed();
            userSeeder.seed();
            employeeSeeder.seed();
            carSeeder.seed();
            subscriptionSeeder.seed();
            
            log.info("Database seeding completed successfully!");
        } catch (Exception e) {
            log.error("Error during database seeding: {}", e.getMessage(), e);
            throw e;
        }
    }
}
