package org.common.models;

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
public class KeyPairRSA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Convert(converter = org.common.converter.PublicKeyConverter.class)
    private PublicKey publicKey;

    @Lob
    @Convert(converter = org.common.converter.PrivateKeyConverter.class)
    private PrivateKey privateKey;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
