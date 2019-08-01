package pl.coderslab.charity.entity.authentication;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.charity.validator.NotBlank;
import pl.coderslab.charity.validator.ValidationByAdminGroup;
import pl.coderslab.charity.validator.ValidationByUserGroup;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 25, groups = {ValidationByUserGroup.class})
    @NotBlank(groups = {ValidationByUserGroup.class})
    private String username;
    @Column(nullable = false)
    @Email(groups = {ValidationByUserGroup.class})
    @NotBlank(groups = {ValidationByUserGroup.class})
    private String email;
    @Column(nullable = false)
    @NotBlank(groups = {ValidationByUserGroup.class})
    @Size(min = 5, groups = {ValidationByUserGroup.class})
    private String password;
    private boolean enabled;
    @NotEmpty(groups = {ValidationByAdminGroup.class})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(String username, String email, String password, boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }
}
