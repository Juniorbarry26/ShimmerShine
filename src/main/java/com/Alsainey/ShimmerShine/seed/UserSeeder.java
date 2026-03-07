package com.Alsainey.ShimmerShine.seed;

import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.entities.subscription.SubscriptionPlan;
import com.Alsainey.ShimmerShine.role.Role;
import com.Alsainey.ShimmerShine.role.RoleRepository;
import com.Alsainey.ShimmerShine.repositories.subscription.SubscriptionPlanRepository;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSeeder implements DatabaseSeeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void seed() {
        log.info("UserSeeder: Starting user seeding...");
        log.info("UserSeeder: Current user count: {}", userRepository.count());
        
        if (userRepository.count() == 0) {
            log.info("UserSeeder: Seeding users...");
            
            try {
                // Get roles and plans
                Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
                Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("User role not found"));
                
                log.info("UserSeeder: Found roles - admin: {}, user: {}", adminRole.getName(), userRole.getName());
                
                SubscriptionPlan basicPlan = subscriptionPlanRepository.findByName(PlanName.BASIC)
                    .orElseThrow(() -> new RuntimeException("Basic plan not found"));
                SubscriptionPlan premiumPlan = subscriptionPlanRepository.findByName(PlanName.PREMIUM)
                    .orElseThrow(() -> new RuntimeException("Premium plan not found"));
                
                log.info("UserSeeder: Found plans - basic: {}, premium: {}", basicPlan.getName(), premiumPlan.getName());
            
            List<User> users = Arrays.asList(
                // Admin user
                User.builder()
                    .firstname("Admin")
                    .lastname("User")
                    .email("admin@shimmershine.com")
                    .password(passwordEncoder.encode("admin123"))
                    .phoneNumber("+1234567890")
                    .dateOfBirth(LocalDate.of(1990, 1, 1))
                    .accountLocked(false)
                    .enabled(true)
                    .subscriptionPlan(premiumPlan)
                    .roles(Arrays.asList(adminRole, userRole))
                    .createdDate(LocalDateTime.now())
                    .build(),
                
                // Regular users
                User.builder()
                    .firstname("John")
                    .lastname("Doe")
                    .email("john.doe@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .phoneNumber("+1234567891")
                    .dateOfBirth(LocalDate.of(1985, 5, 15))
                    .accountLocked(false)
                    .enabled(true)
                    .subscriptionPlan(basicPlan)
                    .roles(Arrays.asList(userRole))
                    .createdDate(LocalDateTime.now())
                    .build(),
                
                User.builder()
                    .firstname("Jane")
                    .lastname("Smith")
                    .email("jane.smith@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .phoneNumber("+1234567892")
                    .dateOfBirth(LocalDate.of(1992, 8, 22))
                    .accountLocked(false)
                    .enabled(true)
                    .subscriptionPlan(premiumPlan)
                    .roles(Arrays.asList(userRole))
                    .createdDate(LocalDateTime.now())
                    .build(),
                
                User.builder()
                    .firstname("Mike")
                    .lastname("Johnson")
                    .email("mike.johnson@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .phoneNumber("+1234567893")
                    .dateOfBirth(LocalDate.of(1988, 12, 3))
                    .accountLocked(false)
                    .enabled(true)
                    .subscriptionPlan(basicPlan)
                    .roles(Arrays.asList(userRole))
                    .createdDate(LocalDateTime.now())
                    .build(),
                
                User.builder()
                    .firstname("Sarah")
                    .lastname("Williams")
                    .email("sarah.williams@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .phoneNumber("+1234567894")
                    .dateOfBirth(LocalDate.of(1995, 3, 10))
                    .accountLocked(false)
                    .enabled(true)
                    .subscriptionPlan(premiumPlan)
                    .roles(Arrays.asList(userRole))
                    .createdDate(LocalDateTime.now())
                    .build()
            );
            
            log.info("UserSeeder: Created {} user objects to save", users.size());
            userRepository.saveAll(users);
            log.info("UserSeeder: Successfully saved {} users", users.size());
            log.info("UserSeeder: New user count: {}", userRepository.count());
            
            } catch (Exception e) {
                log.error("UserSeeder: Error during user seeding: {}", e.getMessage(), e);
                throw e;
            }
        } else {
            log.info("UserSeeder: Users already exist, skipping seeding");
        }
    }
}
