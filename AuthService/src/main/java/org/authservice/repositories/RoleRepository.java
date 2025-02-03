package org.authservice.repositories;

import org.common.models.Role;
import org.common.models.RoleState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRolstate(RoleState rolstate);
}
