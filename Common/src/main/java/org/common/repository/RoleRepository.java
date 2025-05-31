package org.common.repositories;

import org.common.model.Role;
import org.common.model.RoleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleState(RoleState roleState);
}