package org.authservice.repositories;

import org.common.models.KeyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyPairRepository extends JpaRepository<KeyPair, Long> {
    PublicKeyProjection findByIdAndPublicKeyIsNotNull(Long id);
    PrivateKeyProjection findByIdAndPrivateKeyIsNotNull(Long id);
}
