package org.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        indexes = @Index(name = "idx_name", columnList = "name")
)
@EqualsAndHashCode(exclude = {"posts"})
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private Set<Post> posts;
}
