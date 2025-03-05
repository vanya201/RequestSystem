package org.common.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.EnumType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @Enumerated(EnumType.STRING)
    private ReactionStatus reaction;

}

