package org.common.seeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.common.model.Role;
import org.common.model.RoleState;
import org.common.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleSeeder {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void seedRoles() {
        createRoleIfNotExists(RoleState.ADMIN);
        createRoleIfNotExists(RoleState.USER);
    }

    private void createRoleIfNotExists(RoleState roleState) {
        roleRepository.findByRoleState(roleState).orElseGet(() -> {
            Role role = Role.builder()
                    .roleState(roleState)
                    .build();
            return roleRepository.save(role);
        });
    }
}