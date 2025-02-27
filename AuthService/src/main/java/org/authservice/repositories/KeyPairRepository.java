package org.authservice.repositories;

import org.common.models.KeyPairRSA;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyPairRepository extends JpaRepository<KeyPairRSA, Long> {

    @Cacheable(value = "pairKey", key = "'publicKey'")
    PublicKeyProjection findFirstByPublicKeyIsNotNullOrderByCreatedAtDesc();

    @Cacheable(value = "pairKey", key = "'privateKey'")
    PrivateKeyProjection findFirstByEncryptPrivateKeyIsNotNullOrderByCreatedAtDesc();
}

