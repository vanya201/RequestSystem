package org.common.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String publicKey;

    @Lob
    //TODO: make it an encrypted field
    private String privateKey;

    //TODO: added Date created
}
