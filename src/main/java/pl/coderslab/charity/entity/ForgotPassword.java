package pl.coderslab.charity.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.charity.entity.authentication.User;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "forgot_passwords")
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String token;

    public ForgotPassword(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
