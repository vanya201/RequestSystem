package org.common.repository;

import org.common.models.KeyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyPairRepository extends JpaRepository<KeyPair, Long> {

}
