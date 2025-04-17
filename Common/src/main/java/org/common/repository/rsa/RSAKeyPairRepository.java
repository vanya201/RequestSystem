package org.common.repository.rsa;

import org.common.model.RSAKeyPair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSAKeyPairRepository extends JpaRepository<RSAKeyPair, Long> {

    @Cacheable(value = "pairKey", key = "'publicKey'")
    PublicKeyProjection findFirstByPublicKeyIsNotNullOrderByCreatedAtDesc();

    @Cacheable(value = "pairKey", key = "'privateKey'")
    PrivateKeyProjection findFirstByPrivateKeyIsNotNullOrderByCreatedAtDesc();
}

