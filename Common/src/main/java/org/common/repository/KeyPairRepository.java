package org.common.repository;

import org.common.models.KeyPairRSA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyPairRepository extends JpaRepository<KeyPairRSA, Long> {

}
