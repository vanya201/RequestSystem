package org.common.model;
import jakarta.persistence.*;
import lombok.*;
import org.common.converter.PasswordConverter;

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



    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
                    name = "fk_user_roles_user",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
            )),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(
                    name = "fk_user_roles_role",
                    foreignKeyDefinition = "FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE"
            ))
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
                    name = "fk_user_friends_user",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
            )),
            inverseJoinColumns = @JoinColumn(name = "friend_id", foreignKey = @ForeignKey(
                    name = "fk_user_friends_friend",
                    foreignKeyDefinition = "FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE"
            ))
    )
    @Builder.Default
    private Set<User> friends = new LinkedHashSet<>();



    public void setFriend(User user) {
        getFriends().add(user);
        user.getFriends().add(this);
    }

    public boolean removeFriend(User user) {
        return this.getFriends().remove(user)
                ||
                user.getFriends().remove(this);
    }
}

