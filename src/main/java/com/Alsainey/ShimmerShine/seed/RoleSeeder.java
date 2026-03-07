package com.Alsainey.ShimmerShine.seed;

import com.Alsainey.ShimmerShine.role.Role;
import com.Alsainey.ShimmerShine.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleSeeder implements DatabaseSeeder {

    private final RoleRepository roleRepository;

    @Override
    public void seed() {
        if (roleRepository.count() == 0) {
            log.info("Seeding roles...");
            
            List<Role> roles = Arrays.asList(
                Role.builder()
                    .name("ROLE_ADMIN")
                    .build(),
                Role.builder()
                    .name("ROLE_MANAGER")
                    .build(),
                Role.builder()
                    .name("ROLE_EMPLOYEE")
                    .build(),
                Role.builder()
                    .name("ROLE_USER")
                    .build()
            );
            
            roleRepository.saveAll(roles);
            log.info("Seeded {} roles", roles.size());
        } else {
            log.info("Roles already exist, skipping seeding");
        }
    }
}
