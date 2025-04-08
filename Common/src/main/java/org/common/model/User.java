package org.common.model;
import jakarta.persistence.*;
import lombok.*;
import org.common.converter.PasswordConverter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_username", columnList = "username")
        }
)
@EqualsAndHashCode(of = {"username", "email"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String username;


    @Convert(converter = PasswordConverter.class)
    private String password;



    @Column(unique = true)
    private String email;



    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();



    @ManyToMany
    @Builder.Default
    @JoinTable(
            name = "friend_ship",
            joinColumns = @JoinColumn(name = "user1_id"),
            inverseJoinColumns = @JoinColumn(name = "user2_id")
    )
    private Set<User> friends = new LinkedHashSet<>();
}

