package org.authservice.service;

import lombok.RequiredArgsConstructor;

import org.common.model.Role;
import org.common.model.RoleState;
import org.common.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Role getByRolState(RoleState roleState) {
       return repository.findByRoleState(roleState).
               orElseThrow(() -> new RuntimeException("Такой рол нету"));
    }

}
