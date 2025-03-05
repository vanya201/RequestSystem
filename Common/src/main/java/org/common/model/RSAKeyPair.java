package org.common.model;

import jakarta.persistence.*;
import lombok.*;
import org.common.converter.PrivateKeyConverter;
import org.common.converter.PublicKeyConverter;
import org.hibernate.annotations.CreationTimestamp;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RSAKeyPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = PublicKeyConverter.class)
    private PublicKey publicKey;

    @Convert(converter = PrivateKeyConverter.class)
    private PrivateKey privateKey;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
